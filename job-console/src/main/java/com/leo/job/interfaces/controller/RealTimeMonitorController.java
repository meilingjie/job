package com.leo.job.interfaces.controller;

import com.alibaba.fastjson.JSONArray;
import com.leo.job.model.TaskInstance;
import com.leo.job.services.TaskInstanceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;

/**
 * @author leo
 * @Description
 * @date 16/4/8 上午9:59
 */
@ServerEndpoint(value = "/realTimeMonitor", configurator = SpringConfigurator.class)
public class RealTimeMonitorController {

    private static Logger LOG = LogManager.getLogger(TaskController.class);
    @Inject
    private TaskInstanceService taskInstanceService;

    @OnMessage
    public void onMessage(String message, Session session)
            throws IOException, InterruptedException, EncodeException {

        LOG.info("Received: " + message);

        while (true) {
            // 每隔1分钟推送一次
            Thread.sleep(60000);
            List<TaskInstance> taskList = taskInstanceService.queryAllTaskInstances();
            JSONArray ja = new JSONArray();
            ja.addAll(taskList);
            session.getBasicRemote().sendText(ja.toString());
        }


    }

    @OnOpen
    public void onOpen() {
        LOG.info("Client connected");
    }

    @OnClose
    public void onClose() {
        LOG.info("Connection closed");
    }

    @OnError
    public void onError(Throwable throwable) {
        LOG.info("Connection is error:" + throwable.getMessage());
    }
}
