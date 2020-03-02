package when.injection;

/**
 * @author: when
 * @create: 2020-03-02  15:02
 **/
public class NoSuchBeanDefinitionException extends RuntimeException {
    public NoSuchBeanDefinitionException(String message) {
        super(message);
    }
}
