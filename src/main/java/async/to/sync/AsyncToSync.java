package async.to.sync;

import async.to.sync.response.ResponseContainer;
import async.to.sync.ws.WebSocketClientManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ServletComponentScan
@RestController
public class AsyncToSync {

    private static int clientNum = 1200;

    //没用
    private static int tpsPerClient = 2000;

    @Autowired
    WebSocketClientManager webSocketClientManager;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AsyncToSync.class, args);

        ResponseContainer container = new ResponseContainer();
        WebSocketClientManager clientManager = new WebSocketClientManager(container);

        Thread.sleep(3000);

        //Mock client
        for (int i = 0; i < clientNum; i++) {
            Client client = new Client(container, clientManager, tpsPerClient);
            client.start();
        }
    }
}
