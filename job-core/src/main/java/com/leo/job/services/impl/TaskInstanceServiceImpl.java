package com.leo.job.services.impl;

import com.leo.job.model.TaskInstance;
import com.leo.job.repositories.TaskInstanceRepository;
import com.leo.job.services.TaskInstanceService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @Description 任务实例 service
 * @author leo
 * @date 2015年10月2日上午11:14:03
 */
@Named
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class TaskInstanceServiceImpl implements TaskInstanceService {

	@Inject
	private TaskInstanceRepository taskInstanceRepository;

	@Override
	public int insertTaskInstance(TaskInstance taskInstance) {
		return taskInstanceRepository.insertTaskInstance(taskInstance);
	}

	@Override
	public List<TaskInstance> queryTaskInstanceByTaskDefId(Long taskDefId) {
		return taskInstanceRepository.queryTaskInstanceByTaskDefId(taskDefId);
	}

	@Override
	public List<TaskInstance> queryAllTaskInstances() {
		return taskInstanceRepository.queryAllTaskInstances();
	}

	@Override
	public TaskInstance queryTaskInstanceById(Long id) {
		return taskInstanceRepository.queryTaskInstanceById(id);
	}

	@Override
	public int updateTaskInstance(TaskInstance taskInstance) {
		return taskInstanceRepository.updateTaskInstance(taskInstance);
	}

	@Override
	public int updateTaskQueue(TaskInstance taskInstance) {
		return taskInstanceRepository.updateTaskQueue(taskInstance);
	}

	@Override
	public int deleteTaskInstanceById(Long id) {
		return taskInstanceRepository.deleteTaskInstanceById(id);
	}

}
