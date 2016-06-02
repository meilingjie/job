package com.leo.job.handle;

import com.leo.job.model.TaskDef;
import com.leo.job.model.TaskError;
import com.leo.job.model.TaskInstance;
import com.leo.job.model.TaskParamValue;
import com.leo.job.services.*;
import com.leo.job.support.TaskInstanceModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author leo
 * @Description 任务执行引擎
 * @date 2015年8月25日上午10:49:54
 */
public class SchedulerEngine {

	private static Logger LOG = LogManager.getLogger(SchedulerEngine.class);
	@Inject
	private TaskErrorService taskErrorService;
	@Inject
	private TaskInstanceService taskService;
	@Inject
	private TaskDefService taskDefService;
	@Inject
	private TaskParamValueService taskParamValueService;
	@Inject
	private ApplicationContext appContext;
	private Scheduler scheduler;

	@PostConstruct
	public void boot() {
		try {
			this.scheduler = StdSchedulerFactory.getDefaultScheduler();

			SchedulerContext context = this.scheduler.getContext();
			context.put("taskErrorService", taskErrorService);
			context.put("taskService", taskService);

			Executors.newSingleThreadExecutor().execute(new Runnable() {
				public void run() {
					SchedulerEngine.this.load();
				}
			});
			this.scheduler.start();
		} catch (SchedulerException e) {
			LOG.error("quartz scheduler start failed ".concat(e.getMessage()), e);
		}
	}

	@PreDestroy
	public void  destroy(){
		try {
			LOG.debug("quartz scheduler is shutdown....");
			this.scheduler = StdSchedulerFactory.getDefaultScheduler();
			if(this.scheduler.isStarted()){
				this.scheduler.pauseAll();

				// 判断还在运行中的任务
				List<JobExecutionContext> cejList = this.scheduler.getCurrentlyExecutingJobs();
				for (JobExecutionContext job : cejList) {
					this.scheduler.interrupt(job.getTrigger().getJobKey());
				}

				while (this.scheduler.getCurrentlyExecutingJobs().size() > 0) {
					Thread.sleep(1000);
					this.scheduler.pauseAll();
				}

				this.scheduler.shutdown();
			}
		} catch (SchedulerException e) {
			LOG.error("quartz scheduler shutdown failed ".concat(e.getMessage()), e);
		} catch (InterruptedException e) {
			LOG.error("quartz scheduler shutdown failed ".concat(e.getMessage()), e);
		}
	}

	public boolean isStarted() {
		if (this.scheduler == null)
			return false;
		try {
			return this.scheduler.isStarted();
		} catch (SchedulerException e) {
			LOG.error(e.getMessage());
		}
		return false;
	}

	private void load() {
		List<TaskDef> defList = taskDefService.queryTaskDefList();
		for (TaskDef taskDef : defList) {
			List<TaskInstance> list = taskService.queryTaskInstanceByTaskDefId(taskDef.getId());
			for (TaskInstance task : list) {
				if (task.getAutoFlag() == 1) {
					TaskLogic taskLogic =
							appContext.getBean(taskDef.getAlgorithm(), TaskLogic.class);
					addJob(autoAssembleModel(task), taskLogic);
				}
			}
		}
	}

	private TaskInstanceModel autoAssembleModel(TaskInstance ti) {
		TaskInstanceModel tm = new TaskInstanceModel();

		tm.setTaskInstance(ti);
		List<TaskParamValue> list = taskParamValueService.queryTaskParamValuesByTaskInsId(ti.getId());
		tm.setParamVals(list);

		return tm;
	}

	public void reboot() {
		if (this.scheduler != null) {
			shutdown();
		}
		boot();
	}

	public void shutdown() {
		if (this.scheduler != null)
			try {
				this.scheduler.clear();
				this.scheduler.shutdown(true);
			} catch (SchedulerException e) {
				LOG.error("something wrong! shutdown quartz scheduler failed. "
						.concat(e.getMessage()), e);
			}
	}

	public void addJob(TaskInstanceModel task, TaskLogic taskLogic) {
		try {
			String taskName = JobMapper.taskName(task);
			String taskGroup = JobMapper.taskGroup(task);
			this.scheduler.scheduleJob(
					JobMapper.jobDetail(taskName, taskGroup, JobMapper.jobDataMap(task, taskLogic)),
					JobMapper.trigger(task));
		} catch (SchedulerException e) {
			LOG.error("error occured when taskrunner the job whose id is "
					.concat(task.getTaskInstance().getId().toString()), e);
		}
	}

	public void addImmediateJob(TaskInstanceModel task, TaskLogic taskLogic, DateTime fireTime) {
		try {
			String taskName = JobMapper.taskName(task);
			String taskGroup = JobMapper.throwawayTaskGroup();
			this.scheduler.scheduleJob(
					JobMapper.jobDetail(taskName, taskGroup,
							JobMapper.jobDataMap(task, taskLogic, null, fireTime)),
					JobMapper.triggerNow(taskName, taskGroup));
		} catch (SchedulerException e) {
			LOG.error("error occured when taskrunner the job whose id is "
					.concat(task.getTaskInstance().getId().toString()), e);
		}
	}

	public void addReCalcJob(TaskInstanceModel task, TaskLogic taskLogic, TaskError info) {
		try {
			String taskName = JobMapper.taskName(task, info);
			String taskGroup = JobMapper.throwawayTaskGroup();
			this.scheduler.scheduleJob(
					JobMapper.jobDetail(taskName, taskGroup,
							JobMapper.jobDataMap(task, taskLogic, info)),
					JobMapper.triggerNow(taskName, taskGroup));
		} catch (SchedulerException e) {
			LOG.error("error occured when taskrunner the job whose id is "
						.concat(task.getTaskInstance().getId().toString()), e);
		}
	}

	public void removeJob(TaskInstanceModel task) {
		try {
			JobKey jobKey = JobMapper.jobKey(JobMapper.taskName(task), JobMapper.taskGroup(task));
			this.scheduler.pauseJob(jobKey);
			this.scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			LOG.error("failed to remove the job whose identity is "
						.concat(task.getTaskInstance().getId().toString()).concat("."), e);
		}
	}

	public void updateJob(TaskInstanceModel task, TaskLogic taskLogic) {
		removeJob(task);
		addJob(task, taskLogic);
	}

}
