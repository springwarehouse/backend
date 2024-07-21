package com.example.message.service.ws;

import com.example.message.core.exception.WSException;
import com.example.message.data.ws.*;
import com.example.message.eventbus.template.event.StateBStartEvent;
import com.example.message.eventbus.template.event.StateBStopEvent;
import com.example.message.eventbus.template.event.StateCStartEvent;
import com.example.message.eventbus.template.event.StateCStopEvent;
import com.example.message.data.ws.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TemplateHandler {
    public static final String Prefix = "template.";

    private static final String MODULE_TEMPLATE = "template1";

    private static final String TEMPLATE_START = "start";
    private static final String TEMPLATE_STOP = "stop";

    // 合法的监控模块名称列表
    private static final List<String> LEGAL_MODULES = Lists.newArrayList(
            MODULE_TEMPLATE
    );

    private final ObjectMapper objectMapper;

    private final HashMap<ModuleAndTemplateId, List<WebSocketSession>> templateSessions = new HashMap<>();

    @Subscribe
    public void onEvent(StateBStartEvent e) {
        TemplateRespVO respVO = TemplateRespVO.builder()
                .module(MODULE_TEMPLATE)
                .templateId(e.getEventId())
                .time(e.getStartTime())
                .type(WSEventType.StartB).build();
        broadcast(respVO);
    }

    @Subscribe
    public void onEvent(StateBStopEvent e) {
        TemplateRespVO respVO = TemplateRespVO.builder()
                .module(MODULE_TEMPLATE)
                .templateId(e.getEventId())
                .time(e.getStartTime())
                .type(WSEventType.StartB).build();
        broadcast(respVO);
    }

    @Subscribe
    public void onEvent(StateCStartEvent e) {
        TemplateRespVO respVO = TemplateRespVO.builder()
                .module(MODULE_TEMPLATE)
                .templateId(e.getEventId())
                .time(e.getStartTime())
                .type(WSEventType.StartC).build();
        broadcast(respVO);
    }

    @Subscribe
    public void onEvent(StateCStopEvent e) {
        TemplateRespVO respVO = TemplateRespVO.builder()
                .module(MODULE_TEMPLATE)
                .templateId(e.getEventId())
                .time(e.getStartTime())
                .type(WSEventType.StartC).build();
        broadcast(respVO);
    }

    private void broadcast(TemplateRespVO resp) {
        List<WebSocketSession> sessions = null;
        synchronized (this) {
            List<WebSocketSession> _sessions = templateSessions.get(new ModuleAndTemplateId(resp.getModule(), resp.getTemplateId()));
            if (_sessions != null) {
                sessions = new ArrayList<>(_sessions);
            }
        }
        if (sessions != null && !sessions.isEmpty()) {
            try {
                String response = objectMapper.writeValueAsString(WSResponse.push("template1.data", resp));
                sessions.forEach((session) -> {
                    try {
                        session.sendMessage(new TextMessage(response));
                    } catch (Exception e) {
                        log.error("broadcast message to session error", e);
                    }
                });
            } catch (Exception e) {
                log.error("broadcast message to session error", e);
            }
        }
    }

    // 客户端注册一个监控模块, 需要发送监控的 module 名称
    private void handleMonitorStart(WSService wsService, WebSocketSession session, WSMessage wsMessage) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("template start: {}", wsMessage);
        }
        JsonNode data = wsMessage.getData();
        if (data == null) {
            throw new WSException("no data found in start");
        }
        TemplateReqVO reqVO = objectMapper.treeToValue(data, TemplateReqVO.class);
        if (reqVO.getModule() == null) {
            throw new WSException("no module name found in start");
        }
        if (reqVO.getTemplateId() == null) {
            throw new WSException("no template id found in start");
        }
        if (!LEGAL_MODULES.contains(reqVO.getModule())) {
            throw new WSException("illegal module name: " + reqVO.getModule());
        }
        // 将 session 添加到对应的 module 监控列表中
        synchronized (this) {
            ModuleAndTemplateId moduleAndDeviceId = new ModuleAndTemplateId(reqVO.getModule(), reqVO.getTemplateId());
            List<WebSocketSession> list = templateSessions.computeIfAbsent(moduleAndDeviceId, k -> new ArrayList<>());
            if (!list.contains(session)) {
                list.add(session);
            }
        }
        wsService.responseData(session, wsMessage.getId(), wsMessage.getRoute(), null);
        if (log.isDebugEnabled()) {
            log.debug("monitor start success: {}", debugSessions());
        }
    }

    private void handleMonitorStop(WSService wsService, WebSocketSession session, WSMessage wsMessage) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("template start: {}", wsMessage);
        }
        JsonNode data = wsMessage.getData();
        if (data == null) {
            throw new WSException("no data found in stop");
        }
        TemplateReqVO reqVO = objectMapper.treeToValue(data, TemplateReqVO.class);
        if (reqVO.getModule() == null) {
            throw new WSException("no module name found in stop");
        }
        if (!LEGAL_MODULES.contains(reqVO.getModule())) {
            throw new WSException("illegal module name: " + reqVO.getModule());
        }
        synchronized (this) {
            if (reqVO.getTemplateId() == null) {
                // 移除 module 中对所有设备的监控列表中的 session
                for (ModuleAndTemplateId moduleAndDeviceId : templateSessions.keySet()) {
                    if (moduleAndDeviceId.getModule().equals(reqVO.getModule())) {
                        List<WebSocketSession> list = templateSessions.get(moduleAndDeviceId);
                        if (list != null) {
                            list.remove(session);
                        }
                    }
                }
            } else {
                ModuleAndTemplateId moduleAndDeviceId = new ModuleAndTemplateId(reqVO.getModule(), reqVO.getTemplateId());
                templateSessions.remove(moduleAndDeviceId);
                List<WebSocketSession> list = templateSessions.get(moduleAndDeviceId);
                if (list != null) {
                    list.remove(session);
                }
            }
        }
        wsService.responseData(session, wsMessage.getId(), wsMessage.getRoute(), null);
        if (log.isDebugEnabled()) {
            log.debug("monitor stop success: {}", debugSessions());
        }
    }

    private HashMap<String, List<String>> debugSessions() {
        HashMap<String, List<String>> result = new HashMap<>();
        synchronized (this) {
            templateSessions.forEach((k, v) -> {
                List<String> list = new ArrayList<>();
                v.forEach((session) -> list.add(session.getId()));
                result.put(k.getModule() + "-" + k.getTemplateId(), list);
            });
        }
        return result;
    }

    // 服务端主动关闭 session 时, 需要将 session 从所有监控模块的监控列表中移除
    public void removeSession(WebSocketSession session) {
        if (log.isDebugEnabled()) {
            log.debug("monitor remove sessioin: {}", session.getId());
        }
        synchronized (this) {
            templateSessions.values().forEach((sessions) -> sessions.remove(session));
        }
        if (log.isDebugEnabled()) {
            log.debug("monitor remove success: {}", debugSessions());
        }
    }

    public void handle(WSService wsService, WebSocketSession session, WSMessage wsMessage) throws Exception {
        String route = wsMessage.getRoute().substring(Prefix.length());
        if (route.equals(TEMPLATE_START)) {
            handleMonitorStart(wsService, session, wsMessage);
        } else if (route.equals(TEMPLATE_STOP)) {
            handleMonitorStop(wsService, session, wsMessage);
        } else {
            throw new WSException("unknown route: " + wsMessage.getRoute());
        }
    }

    /**
     *
     */
    @Data
    @AllArgsConstructor
    private static class ModuleAndTemplateId {
        private String module;
        private String templateId;
    }
}
