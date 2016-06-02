package com.leo.job.model;

import java.io.Serializable;

/**
 * @author leo
 * @Description 任务定义
 * @date 2015年9月30日上午10:49:54
 */
public class TaskDef implements Serializable{
    private Long id;
    private Integer taskType;
    private Short privateFlag;
    private String taskName;
    private String algorithm;
    private static final long serialVersionUID = -2112284530787370048L;;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}
