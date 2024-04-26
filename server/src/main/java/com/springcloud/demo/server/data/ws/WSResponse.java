package com.springcloud.demo.server.data.ws;

import com.springcloud.demo.server.data.constant.WSResponseType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * WebSocket响应消息
 *
 * @param <T>
 */
@Data
@AllArgsConstructor
public class WSResponse<T> {

    /**
     * 消息ID (只在 response 时有用)
     */
    private Long id;

    /**
     * 响应类型
     */
    private WSResponseType type;

    /**
     * 路由(只在 push 时有用)
     */
    private String route;

    /**
     * 消息数据
     */
    private T data;

    /**
     * 构建响应消息
     *
     * @param id
     * @param route
     * @param data
     * @param <T>
     * @return
     */
    public static <T> WSResponse<T> response(Long id, String route, T data) {
        return new WSResponse<T>(id, WSResponseType.response, route, data);
    }

    /**
     * 构建推送消息
     *
     * @param route
     * @param data
     * @param <T>
     * @return
     */
    public static <T> WSResponse<T> push(String route, T data) {
        return new WSResponse<T>(0L, WSResponseType.push, route, data);
    }

    /**
     * 构建心跳消息
     *
     * @return
     */
    public static WSResponse<Object> heartbeat() {
        return new WSResponse<Object>(0L, WSResponseType.heartbeat, null, null);
    }
}
