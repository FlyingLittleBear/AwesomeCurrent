package async.to.sync.ws.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;


@ServerEndpoint(value = "/wsServer", configurator = HttpSessionConfigurator.class)
@Component
public class WebSocket {
    private Logger logger = LoggerFactory.getLogger(WebSocket.class);

    private Session webSocketSession;

    private HttpSession httpSession;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        logger.info("ws server is open!");
        this.webSocketSession = session;
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        System.out.println(this.httpSession.getId());
    }

    @OnClose
    public void onClose() {
        logger.info("ws server is close!");
    }

    @OnMessage
    public void onMessage(String message) {
        logger.debug("Server receive message:" + message);

        try {
            this.webSocketSession.getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.error("Web socket return exception:", e);
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Throwable error) {
        logger.error("ws server is error!", error);
        error.printStackTrace();
    }

}

