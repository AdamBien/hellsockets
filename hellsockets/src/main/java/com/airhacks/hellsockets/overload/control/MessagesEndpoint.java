package com.airhacks.hellsockets.overload.control;

import java.io.IOException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

/**
 *
 * @author airhacks.com
 */
public class MessagesEndpoint extends Endpoint {

    private Session session;

    private String message;

    @Override
    public void onOpen(Session session, EndpointConfig ec) {
        this.session = session;
        //lambda cannot be used for MessageHandlers
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String msg) {
                message = msg;
            }
        });
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public String getMessage() {
        return message;
    }

}
