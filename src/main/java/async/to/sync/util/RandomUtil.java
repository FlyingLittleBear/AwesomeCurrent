package async.to.sync.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    public static long getLong() {
        return ThreadLocalRandom.current().nextLong();
    }

    public static int getInt() {
        return ThreadLocalRandom.current().nextInt();
    }
}
