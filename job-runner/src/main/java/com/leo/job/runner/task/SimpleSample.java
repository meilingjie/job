package com.leo.job.runner.task;

import com.leo.job.exception.TaskException;
import com.leo.job.services.TaskLogic;
import com.leo.job.support.TaskParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import javax.inject.Named;
import java.util.Map;

/**
 * @Description 代付 任务
 * @author leo
 * @date 2015年9月30日下午3:28:45
 */
@Named("simpleSample")
public class SimpleSample implements TaskLogic {

	private final static Logger LOG = LogManager.getLogger(SimpleSample.class);

	@Override
	public boolean run(DateTime fireTime, Map<String, TaskParam> paraMap, Boolean notifyFlag)
			throws TaskException {

		LOG.info("this is simpleSample....");

		return true;
	}
}
