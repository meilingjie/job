package com.leo.job.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author leo
 * @Description 任务错误信息
 * @date 2015年9月30日上午10:49:54
 */
public class TaskError implements Serializable {
	private Long id;
	private String exceptionInfo;
	private Date runTime;
	private Date failTime;
	private Long taskInsId;
	private Integer exceptionType;
	private Short recoverFlag;
	private String remark;
	private static final long serialVersionUID = 5213122399447761754L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExceptionInfo() {
		return exceptionInfo;
	}

	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}

	public Date getRunTime() {
		return runTime;
	}

	public void setRunTime(Date runTime) {
		this.runTime = runTime;
	}

	public Date getFailTime() {
		return failTime;
	}

	public void setFailTime(Date failTime) {
		this.failTime = failTime;
	}

	public Long getTaskInsId() {
		return taskInsId;
	}

	public void setTaskInsId(Long taskInsId) {
		this.taskInsId = taskInsId;
	}

	public Integer getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(Integer exceptionType) {
		this.exceptionType = exceptionType;
	}

	public Short getRecoverFlag() {
		return recoverFlag;
	}

	public void setRecoverFlag(Short recoverFlag) {
		this.recoverFlag = recoverFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
