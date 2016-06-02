package com.leo.job.services;

import com.leo.job.model.TaskError;

import java.util.List;

/**
 * @Description 异常任务 service
 * @author leo
 * @date 2015年10月2日上午11:14:03
 */
public interface TaskErrorService {
	int insertTaskError(TaskError taskError);

	int updateTaskError(TaskError taskError);

	List<TaskError> queryTaskError(Short exceptionType);

	List<TaskError> queryOtherTaskError(Short taskType);

	List<TaskError> queryTaskErrorByDateAndType(Integer year, Integer month, Integer day, String taskCode, Short exceptionType);

	List<TaskError> queryErrorTaskDate(String taskCode);
}
