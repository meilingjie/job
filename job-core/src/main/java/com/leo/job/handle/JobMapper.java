package com.leo.job.handle;

import com.leo.job.model.TaskError;
import com.leo.job.services.TaskLogic;
import com.leo.job.support.TaskInstanceModel;
import org.joda.time.DateTime;
import org.quartz.*;

import java.util.Date;

/**
 * @author leo
 * @Description 任务 执行参数配置类
 * @date 2015年8月25日上午10:49:54
 */
public class JobMapper {
	public static JobDetail jobDetail(String taskName, String taskGroup, JobDataMap jobDataMap) {
		return jobDetail(DefaultJob.class, taskName, taskGroup, jobDataMap);
	}

	public static JobDetail jobDetail(Class<? extends Job> jobClass, String taskName,
			String taskGroup, JobDataMap jobDataMap) {
		return JobBuilder.newJob(jobClass).withIdentity(taskName, taskGroup)
				.usingJobData(jobDataMap).build();
	}

	public static JobKey jobKey(String taskName, String taskGroup) {
		return JobKey.jobKey(taskName, taskGroup);
	}

	public static String taskGroup(TaskInstanceModel task) {
		if ((task != null) && (task.getTaskInstance() != null)
				&& (task.getTaskInstance().getPrivateFlag().shortValue() == 1)) {
			return "private";
		}
		return "public";
	}

	public static String throwawayTaskGroup() {
		return "throwaway";
	}

	public static String taskName(TaskInstanceModel task) {
		return String.valueOf(task.getTaskInstance().getId());
	}

	public static String taskName(TaskInstanceModel task, TaskError info) {
		return String.valueOf(task.getTaskInstance().getId()) + String.valueOf(info.getId());
	}

	public static JobDataMap jobDataMap(TaskInstanceModel task, TaskLogic taskLogic) {
		return jobDataMap(task, taskLogic, null, null);
	}

	public static JobDataMap jobDataMap(TaskInstanceModel task, TaskLogic taskLogic,
			TaskError info) {
		if (info == null) {
			return jobDataMap(task, taskLogic);
		}
		return jobDataMap(task, taskLogic, info, new DateTime(info.getRunTime()));
	}

	public static JobDataMap jobDataMap(TaskInstanceModel task, TaskLogic taskLogic, TaskError info,
			DateTime fireTime) {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("task", task);
		jobDataMap.put("taskLogic", taskLogic);
		jobDataMap.put("taskError", info);
		jobDataMap.put("fireTime", fireTime);
		return jobDataMap;
	}

	public static Trigger trigger(TaskInstanceModel task) {
		String jobName = taskName(task);
		String jobGroup = taskGroup(task);
		return TriggerBuilder.newTrigger().withIdentity(triggerName(jobName), jobGroup)
				.forJob(jobName, jobGroup)
				.withSchedule(
						CronScheduleBuilder.cronSchedule(task.getTaskInstance().getSchPattern()))
				.build();
	}

	public static Trigger trigger(TaskInstanceModel task, Date date) {
		String jobName = taskName(task);
		String jobGroup = taskGroup(task);
		return trigger(jobName, jobGroup, date);
	}

	private static Trigger trigger(String jobName, String jobGroup, Date date) {
		return TriggerBuilder.newTrigger().withIdentity(triggerName(jobName), jobGroup)
				.forJob(jobName, jobGroup).startAt(date).build();
	}

	public static Trigger triggerNow(TaskInstanceModel task) {
		String jobName = taskName(task);
		String jobGroup = taskGroup(task);
		return triggerNow(jobName, jobGroup);
	}

	public static Trigger triggerNow(String jobName, String jobGroup) {
		return TriggerBuilder.newTrigger().withIdentity(triggerName(jobName), jobGroup)
				.forJob(jobName, jobGroup).startNow().build();
	}

	public static String triggerName(String jobName) {
		return "Trigger_".concat(jobName);
	}

}
