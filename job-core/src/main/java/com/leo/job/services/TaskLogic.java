package com.leo.job.services;

import com.leo.job.exception.TaskException;
import com.leo.job.support.TaskParam;
import org.joda.time.DateTime;

import java.util.Map;

/**
 * @Description 任务 接口
 * @author leo
 * @date 2015年9月30日下午3:28:45
 */
public abstract interface TaskLogic {
	public abstract boolean run(DateTime paramDateTime, Map<String, TaskParam> paramMap, Boolean notifyFlag)
			throws TaskException;
}
