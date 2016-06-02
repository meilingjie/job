package com.leo.job.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author leo
 * @Description 任务实例
 * @date 2015年9月30日上午10:49:54
 */
public class TaskInstance implements Serializable {
	private Long id;
	private Long taskDefId;
	private String taskCode;
	private Date lastDoneTime;
	private Date nextRunTime;
	private String schPattern;
	/**
	 * 0 手动任务 1 自动任务
	 */
	private Short autoFlag;
	/**
	 * 一个业务模块下 0 单任务 1 多任务
	 */
	private Integer taskType;
	private Short privateFlag;
	private Integer notifyMode;
	private static final long serialVersionUID = 7015348952967669366L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTaskDefId() {
		return taskDefId;
	}

	public void setTaskDefId(Long taskDefId) {
		this.taskDefId = taskDefId;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public Date getLastDoneTime() {
		return lastDoneTime;
	}

	public void setLastDoneTime(Date lastDoneTime) {
		this.lastDoneTime = lastDoneTime;
	}

	public Date getNextRunTime() {
		return nextRunTime;
	}

	public void setNextRunTime(Date nextRunTime) {
		this.nextRunTime = nextRunTime;
	}

	public String getSchPattern() {
		return schPattern;
	}

	public void setSchPattern(String schPattern) {
		this.schPattern = schPattern;
	}

	public Short getAutoFlag() {
		return autoFlag;
	}

	public void setAutoFlag(Short autoFlag) {
		this.autoFlag = autoFlag;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public Short getPrivateFlag() {
		return privateFlag;
	}

	public void setPrivateFlag(Short privateFlag) {
		this.privateFlag = privateFlag;
	}

	public Integer getNotifyMode() {
		return notifyMode;
	}

	public void setNotifyMode(Integer notifyMode) {
		this.notifyMode = notifyMode;
	}

}
