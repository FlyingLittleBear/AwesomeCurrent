package async.to.sync.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ResponseContainer {
    private static final Logger logger = LoggerFactory.getLogger(ResponseContainer.class);

    private Map<String, Response> container = new ConcurrentHashMap<>();

    public void putData(String key, String responseData) {
        logger.debug("Put! container size:{}.Key:{},data:{}.", container.size(), key, responseData);
        Response response = container.get(key);
        if (response == null) {
            logger.warn("Time out response.Drop it!\n Key:{},data:{}", key, responseData);
            return;
        }
        response.setData(responseData);
    }


    public void add(String key, Response response) {
        this.container.put(key, response);
    }

    public Response remove(String key) {
        return this.container.remove(key);
    }

}
