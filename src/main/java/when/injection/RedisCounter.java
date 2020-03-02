package when.injection;

/**
 * @author: when
 * @create: 2020-03-02  16:09
 **/
public class RedisCounter {
    private String ipAddress;
    private String port;

    public RedisCounter(String ipAddress, String port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public int incrementAndGet() {
        System.out.println("Connect to " + this.ipAddress + ":" + this.port);
        return 10;
    }
}
