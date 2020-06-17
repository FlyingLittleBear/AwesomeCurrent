package async.to.sync;

import async.to.sync.response.Response;
import async.to.sync.response.ResponseContainer;
import async.to.sync.util.RandomUtil;
import async.to.sync.ws.WebSocketClientManager;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    private static final long timeout = 500;

    private ResponseContainer container;

    private int tps;

    private WebSocketClientManager clientManager;

    public Client(ResponseContainer responseContainer, WebSocketClientManager clientManager, int tps) {
        this.container = responseContainer;
        this.clientManager = clientManager;
        this.tps = tps;
    }

    @Override
    public void run() {
        for (; ; ) {
            String id = String.valueOf(RandomUtil.getLong());
            String receivedId = sendMessage(id);
            Assert.assertEquals(id, receivedId);
//            try {
//                Thread.sleep(1000L / tps);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    private String sendMessage(String msgId) {
        Response response = new Response();
        container.add(msgId, response);

        clientManager.getWalletClient().send(msgId);
        logger.debug("Send msg! id:{},thread id:{}", msgId, Thread.currentThread().getId());
        return getResponse(msgId, response);
    }


    private String getResponse(String msgId, Response response) {
        String responseData = null;
        try {
            synchronized (response) {
                response.wait(timeout);

                if (response.receivedData()) {
                    responseData = response.getData();
                    logger.debug("Got response:{},msgId:{}", responseData, msgId);
                } else {
                    logger.warn("Time out! msgId:{}", msgId);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.container.remove(msgId);
        }
        return responseData;
    }
}
