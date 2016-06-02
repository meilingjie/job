package com.leo.job.support;

/**
 * @author leo
 * @Description 任务执行参数
 * @date 2015年8月25日上午10:49:54
 */
public class TaskParam {
	private String name;
	private int type;
	private String value;

	public TaskParam() {}

	public TaskParam(String name, int type, String value) {
		this.name = name;
		this.type = type;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		TaskParam taskParam = (TaskParam) o;

		if (this.type != taskParam.type) {
			return false;
		}
		if (this.name != null ? !this.name.equals(taskParam.name) : taskParam.name != null) {
			return false;
		}
		if (this.value != null ? !this.value.equals(taskParam.value) : taskParam.value != null) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = this.name != null ? this.name.hashCode() : 0;
		result = 31 * result + this.type;
		result = 31 * result + (this.value != null ? this.value.hashCode() : 0);
		return result;
	}

	public String toString() {
		return "TaskParam{name='" + this.name + '\'' + ", type=" + this.type + ", value='"
				+ this.value + '\'' + '}';
	}
}
