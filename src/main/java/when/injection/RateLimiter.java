package when.injection;

/**
 * @author: when
 * @create: 2020-03-02  15:11
 **/
public class RateLimiter {
    private RedisCounter redisCounter;

    public RateLimiter(RedisCounter redisCounter) {
        this.redisCounter = redisCounter;
    }

    public boolean isValid() {
        this.redisCounter.incrementAndGet();
        return true;
    }

    public void test() {
        System.out.println("test");
    }
}
