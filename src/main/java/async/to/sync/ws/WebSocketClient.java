package async.to.sync.ws;

import async.to.sync.response.ResponseContainer;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;


public class WebSocketClient extends org.java_websocket.client.WebSocketClient {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketClient.class);
    private static final int retryTime = 20;
    private static final int interval = 100;

    private String address;

    private ResponseContainer container;

    public WebSocketClient(String address, ResponseContainer container) throws URISyntaxException {
        super(new URI(address));
        this.address = address;
        this.container = container;
        init();
    }


    @Override
    public void onOpen(ServerHandshake handshakedata) {
        logger.info(address + " is open");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.error(address + " is close.Code:" + code + ",reason:" + reason + ",remote:" + remote);
    }

    @Override
    public void onError(Exception ex) {

    }

    @Override
    public void onMessage(String message) {
        if (isTimeOut()) {
            return;
        }
        container.putData(message, message);

    }


    private void init() {
        this.connect();
        for (int i = 0; i < retryTime; i++) {
            if (this.isOpen()) {
                break;
            }
            try {
                Thread.sleep(interval);
                logger.info("connecting to:" + this.getURI());
            } catch (Exception e) {
                logger.error("fail to init  websocket", e);
            }
        }
        this.setConnectionLostTimeout(0);
    }

    //mock time out
    private boolean isTimeOut() {
        //return RandomUtil.getInt() % 10 == 0;
        return false;
    }
}
