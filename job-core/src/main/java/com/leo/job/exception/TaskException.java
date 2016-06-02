package com.leo.job.exception;

/**
 * @author leo
 * @Description 任务异常
 * @date 2015年8月25日上午10:49:54
 */
public class TaskException extends RuntimeException {
	private static final long serialVersionUID = 3535837954056340839L;

	private int type;

	public TaskException() {}

	public TaskException(int type, String message) {
		super(message);
		this.type = type;
	}

	public TaskException(String message) {
		super(message);
	}

	public TaskException(String message, Throwable cause) {
		super(message, cause);
	}

	public TaskException(Throwable cause) {
		super(cause);
	}

	public TaskException(String message, Throwable cause, boolean enableSuppression,
						 boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
