package when.injection;

/**
 * @author: when
 * @create: 2020-03-02  15:02
 **/
public class BeanCreationFailedException extends RuntimeException {
    public BeanCreationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
