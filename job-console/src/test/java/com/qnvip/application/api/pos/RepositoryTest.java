package com.qnvip.application.api.pos;

import com.leo.job.model.TaskError;
import com.leo.job.repositories.support.MultipleDataSource;
import com.leo.job.services.TaskDefService;
import com.leo.job.services.TaskErrorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class RepositoryTest {
    Logger logger = LogManager.getLogger(RepositoryTest.class);

    @Inject
    private TaskDefService taskDefService;
    @Inject
    private TaskErrorService taskErrorService;

    @Test
    public void taskDefTest() {
        logger.debug(taskDefService.queryTaskDefList().size());
    }

    @Test
    public void updateTaskError() {
        TaskError te = new TaskError();
        te.setId(327L);
        te.setRemark("oooooooo");
        MultipleDataSource.setDataSourceKey("dss");
        taskErrorService.updateTaskError(te);
    }

    @Test
    public void testDTIsAfter() {
        Calendar c = Calendar.getInstance();

        DateTime dt = new DateTime(c.getTime());
        DateTime dt2 = new DateTime(c.getTime());

        System.out.println(dt.isAfter(dt2));
    }

}
