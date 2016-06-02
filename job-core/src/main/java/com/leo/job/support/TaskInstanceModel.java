package com.leo.job.support;

import com.leo.job.model.TaskInstance;
import com.leo.job.model.TaskParamValue;

import java.io.Serializable;
import java.util.List;

/**
 * @author leo
 * @Description 任务执行包装类(带参)
 * @date 2015年8月25日上午10:49:54
 */
public class TaskInstanceModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private TaskInstance taskInstance;
	private List<TaskParamValue> paramVals;

	public TaskInstance getTaskInstance() {
		return this.taskInstance;
	}

	public void setTaskInstance(TaskInstance taskInstance) {
		this.taskInstance = taskInstance;
	}

	public List<TaskParamValue> getParamVals() {
		return this.paramVals;
	}

	public void setParamVals(List<TaskParamValue> paramVals) {
		this.paramVals = paramVals;
	}
}
