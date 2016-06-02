package com.leo.job.handle;

import com.leo.job.exception.TaskException;
import com.leo.job.model.TaskError;
import com.leo.job.model.TaskInstance;
import com.leo.job.model.TaskParamValue;
import com.leo.job.services.TaskErrorService;
import com.leo.job.services.TaskInstanceService;
import com.leo.job.services.TaskLogic;
import com.leo.job.support.TaskInstanceModel;
import com.leo.job.support.TaskParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.quartz.*;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leo
 * @Description 任务执行
 * @date 2015年8月25日上午10:49:54
 */
public class DefaultJob implements InterruptableJob {

	private static Logger LOG = LogManager.getLogger(DefaultJob.class);

	private volatile boolean isJobInterrupted = false;

	private JobKey jobKey = null;

	private volatile Thread thisThread;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		thisThread = Thread.currentThread();
		jobKey = context.getJobDetail().getKey();
		SchedulerContext ctx;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			ctx = context.getScheduler().getContext();
		} catch (SchedulerException e) {
			LOG.error("SchedulerException.", e);
			return;
		}
		TaskErrorService errorService = (TaskErrorService) ctx.get("taskErrorService");
		TaskInstanceService taskService = (TaskInstanceService) ctx.get("taskService");

		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

		TaskInstanceModel task = (TaskInstanceModel) jobDataMap.get("task");
		TaskError taskError = (TaskError) jobDataMap.get("taskError");
		TaskLogic taskLogic = (TaskLogic) jobDataMap.get("taskLogic");
		DateTime fireTime = (DateTime) jobDataMap.get("fireTime");

		TaskInstance taskInstance = task.getTaskInstance();
		taskInstance = taskService.queryTaskInstanceById(taskInstance.getId());

		if (fireTime == null) {
			fireTime = new DateTime(context.getFireTime());
		}

		try {
			Map<String, TaskParam> paraMap = new HashMap();
			List<TaskParamValue> paramVals = task.getParamVals();
			paraMap.put("lastDoneTime", new TaskParam("lastDoneTime", 1, sd.format(taskInstance.getLastDoneTime())));
			if (!CollectionUtils.isEmpty(paramVals)) {
				for (TaskParamValue val : paramVals) {
					paraMap.put(val.getParamName(), new TaskParam(val.getParamName(),
							val.getParamType(), val.getParamVal()));
				}
			}

			Boolean notifyFlag = taskInstance.getNotifyMode() == 0 ? false : true;
			Boolean runFlag = taskLogic.run(fireTime, paraMap, notifyFlag);

			if(runFlag){
				TaskError startInfo = new TaskError();
				startInfo.setExceptionType(0);
				startInfo.setExceptionInfo("任务成功!");
				startInfo.setRunTime(context.getFireTime());
				startInfo.setFailTime(new Date());
				startInfo.setRecoverFlag((short) 0);
				startInfo.setTaskInsId(taskInstance.getId());
				errorService.insertTaskError(startInfo);
			}
			taskInstance.setLastDoneTime(context.getFireTime());

			if (taskError != null) {
				taskError.setRecoverFlag((short) 1);
				errorService.updateTaskError(taskError);
			}
		} catch (TaskException e) {
			TaskError errInfo = new TaskError();

			errInfo.setExceptionType(e.getType());
			errInfo.setExceptionInfo(e.getMessage());
			errInfo.setRunTime(context.getFireTime());
			errInfo.setFailTime(new Date());
			errInfo.setRecoverFlag((short) 0);
			errInfo.setTaskInsId(taskInstance.getId());

			errorService.insertTaskError(errInfo);
			LOG.error("Task exception.", e);
		} catch (Exception e) {
			TaskError errInfo = new TaskError();

			errInfo.setExceptionType(-1);
			if (e.toString().length() > 100) {
				errInfo.setExceptionInfo(e.toString().substring(0, 100));
			} else {
				errInfo.setExceptionInfo(e.toString());
			}
			errInfo.setRunTime(context.getFireTime());
			errInfo.setFailTime(new Date());
			errInfo.setRecoverFlag((short) 0);
			errInfo.setTaskInsId(taskInstance.getId());

			errorService.insertTaskError(errInfo);
			LOG.error("unexpected exception.", e);
		} catch (Throwable e) {
			TaskError errInfo = new TaskError();

			errInfo.setExceptionType(-1);
			if (e.toString().length() > 100) {
				errInfo.setExceptionInfo(e.toString().substring(0, 100));
			} else {
				errInfo.setExceptionInfo(e.toString());
			}
			errInfo.setRunTime(context.getFireTime());
			errInfo.setFailTime(new Date());
			errInfo.setRecoverFlag((short) 0);
			errInfo.setTaskInsId(taskInstance.getId());

			errorService.insertTaskError(errInfo);
			LOG.error("unexpected exception.", e);
		} finally {
			if (isJobInterrupted) {
				LOG.info("Job " + jobKey + " did not complete");
			} else {
				LOG.info("Job " + jobKey + " completed at " + new Date());
			}

			jobDataMap.put("fireTime", context.getNextFireTime());
			taskInstance.setNextRunTime(context.getNextFireTime());
			// 1为任务集群,更新全部任务的上次执行时间
			if(taskInstance.getTaskType() == 1){
				taskService.updateTaskQueue(taskInstance);
			}
			// 自动任务更新相应任务
			if (taskInstance.getAutoFlag() != 0) {
				taskService.updateTaskInstance(taskInstance);
			}
		}
	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		LOG.info("Job " + jobKey + "  -- INTERRUPTING --");
		isJobInterrupted = true;
		if (thisThread != null) {
			// this called cause the ClosedByInterruptException to happen
			thisThread.interrupt();
		}
	}
}
