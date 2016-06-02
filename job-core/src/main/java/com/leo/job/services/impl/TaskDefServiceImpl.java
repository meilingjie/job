package com.leo.job.services.impl;

import com.leo.job.model.TaskDef;
import com.leo.job.repositories.TaskDefRepository;
import com.leo.job.services.TaskDefService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @Description 任务定义 service
 * @author leo
 * @date 2015年10月2日上午11:14:03
 */
@Named
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class TaskDefServiceImpl implements TaskDefService {

	@Inject
	private TaskDefRepository taskDefRepository;

	@Override
	public List<TaskDef> queryTaskDefList() {
		return taskDefRepository.queryTaskDefList();
	}

	@Override
	public int insertTaskDef(TaskDef taskDef) {
		return taskDefRepository.insertTaskDef(taskDef);
	}

	@Override
	public TaskDef queryTaskDefById(Long id) {
		return taskDefRepository.queryTaskDefById(id);
	}
}
