package com.airhacks.hellsockets.overload.boundary;

import com.airhacks.hellsockets.overload.control.MessagesEndpoint;
import com.airhacks.porcupine.execution.boundary.Dedicated;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;

/**
 *
 * @author airhacks.com
 */
public class LoadManager {

    @Inject
    @Dedicated
    ExecutorService loadPool;

    public void start(String uri, String message, int sessions, int iterations) throws DeploymentException, IOException, URISyntaxException, InterruptedException {
        List<MessagesEndpoint> endpoints = endpoints(uri, sessions);
        Collection<Callable<Void>> callables = endpoints.stream().map(e -> convert(e, message)).collect(Collectors.toList());
        for (int i = 0; i < iterations; i++) {
            loadPool.invokeAll(callables);
        }
    }

    List<MessagesEndpoint> endpoints(String uri, int sessions) throws DeploymentException, IOException, URISyntaxException {
        List<MessagesEndpoint> endpoints = new ArrayList<>();
        for (int i = 0; i < sessions; i++) {
            endpoints.add(endpoint(uri));
        }
        return endpoints;
    }

    MessagesEndpoint endpoint(String uri) throws DeploymentException, IOException, URISyntaxException {
        MessagesEndpoint endpoint = new MessagesEndpoint();
        ClientEndpointConfig config = ClientEndpointConfig.Builder.create().build();
        WebSocketContainer provider = ContainerProvider.getWebSocketContainer();
        provider.connectToServer(endpoint, config, new URI(
                uri));
        return endpoint;
    }

    Callable<Void> convert(MessagesEndpoint me, String messageToSend) {
        return () -> {
            try {
                me.sendMessage(messageToSend);
            } catch (IOException ex) {
                Logger.getLogger(LoadManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        };
    }
}
