package com.leo.job.model;

import java.io.Serializable;

/**
 * @author leo
 * @Description 任务参数
 * @date 2015年8月25日上午10:49:54
 */
public class TaskParamValue implements Serializable {
	private Long id;
	private String paramVal;
	private String paramName;
	private Integer paramType;
	private Long taskInsId;
	private static final long serialVersionUID = 2010307013874058143L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParamVal() {
		return paramVal;
	}

	public void setParamVal(String paramVal) {
		this.paramVal = paramVal;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public Integer getParamType() {
		return paramType;
	}

	public void setParamType(Integer paramType) {
		this.paramType = paramType;
	}

	public Long getTaskInsId() {
		return taskInsId;
	}

	public void setTaskInsId(Long taskInsId) {
		this.taskInsId = taskInsId;
	}
}
