package com.ebook.backend.WebSocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/websocket/bookstore/{userId}")
@Component
public class WebSocketServer {
    public WebSocketServer() {
        //每当有一个连接，都会执行一次构造方法
        System.out.println("WebSocket新的连接...");
    }
    private static final AtomicInteger COUNT = new AtomicInteger();
    private static final ConcurrentHashMap<String, Session> SESSIONS = new ConcurrentHashMap<>();
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        //该用户已建立连接，不重复连接
        if (SESSIONS.get(userId) != null) {
            return;
        }
        SESSIONS.put(userId, session);
        COUNT.incrementAndGet();
        System.out.println(userId + "上线了，当前在线人数：" + COUNT);

    }
    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        SESSIONS.remove(userId);
        COUNT.decrementAndGet();
        System.out.println("客户端用户"+userId + "下线了，当前在线人数：" + COUNT);
    }
    //接收消息
    @OnMessage
    public void onMessage(String message) {
        System.out.println("服务器收到消息：" + message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    public void sendMessage(Session toSession, String message) {
        if (toSession != null) {
            try {
                toSession.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("客户端不在线");
        }
    }
    public void sendMessageToUser(String user, String message) {
        System.out.println("sendMessageToUser:sendMessageToUser "+user);
        Session toSession = SESSIONS.get(user);
        sendMessage(toSession, message);
        System.out.println("sendMessageToUser:sendMessageToUser message="+message);
    }
}
