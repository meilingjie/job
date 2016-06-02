package com.leo.job.services.impl;

import com.leo.job.model.TaskError;
import com.leo.job.repositories.TaskErrorRepository;
import com.leo.job.services.TaskErrorService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @Description 异常任务 service
 * @author leo
 * @date 2015年10月2日上午11:14:03
 */
@Named
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class TaskErrorServiceImpl implements TaskErrorService {

	@Inject
	private TaskErrorRepository taskErrorRepository;

	@Override
	public int insertTaskError(TaskError taskError) {
		return taskErrorRepository.insertTaskError(taskError);
	}

	@Override
	public int updateTaskError(TaskError taskError) {
		return taskErrorRepository.updateTaskError(taskError);
	}

	@Override
	public List<TaskError> queryTaskError(Short exceptionType) {
		return taskErrorRepository.queryTaskError(exceptionType);
	}

	@Override
	public List<TaskError> queryOtherTaskError(Short taskType) {
		return taskErrorRepository.queryOtherTaskError(taskType);
	}

	@Override
	public List<TaskError> queryTaskErrorByDateAndType(Integer year, Integer month, Integer day, String taskCode, Short exceptionType){
		return taskErrorRepository.queryTaskErrorByDateAndType(year, month, day, taskCode, exceptionType);
	}

	@Override
	public List<TaskError> queryErrorTaskDate(String taskCode) {
		return taskErrorRepository.queryErrorTaskDate(taskCode);
	}
}
