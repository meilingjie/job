package com.leo.job.services;

import com.leo.job.model.TaskInstance;

import java.util.List;

/**
 * @Description 任务实例 service
 * @author leo
 * @date 2015年10月2日上午11:14:03
 */
public interface TaskInstanceService {
	int insertTaskInstance(TaskInstance taskInstance);

	List<TaskInstance> queryTaskInstanceByTaskDefId(Long taskDefId);


	List<TaskInstance> queryAllTaskInstances();

	TaskInstance queryTaskInstanceById(Long id);

	int updateTaskInstance(TaskInstance taskInstance);

	int updateTaskQueue(TaskInstance taskInstance);

	int deleteTaskInstanceById(Long id);
}
