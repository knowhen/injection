package when.injection;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        RateLimiter retaLimiter = (RateLimiter) applicationContext.getBean("retaLimiter");
        retaLimiter.test();
    }
}
