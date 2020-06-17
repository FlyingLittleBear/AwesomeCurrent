package async.to.sync.ws;

import async.to.sync.response.ResponseContainer;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

@Service
public class WebSocketClientManager {

    private String url = "ws://127.0.0.1:8123/wsServer";

    private WebSocketClient wsClient = null;

    ResponseContainer container;

    public WebSocketClientManager(ResponseContainer container) {
        this.container = container;

        try {
            this.wsClient = new WebSocketClient(url, container);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public WebSocketClient getWalletClient() {
        return this.wsClient;
    }
}
