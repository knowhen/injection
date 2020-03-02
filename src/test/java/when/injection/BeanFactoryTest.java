package when.injection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BeanFactoryTest {
    @Test
    public void testCreateBean() {
//        List<BeanDefinition.ConstructorArg> constructorArgs = new ArrayList<>();
//        BeanDefinition.ConstructorArg constructorArg = new BeanDefinition.ConstructorArg();
//        constructorArg.setRef(true);
//        constructorArg.setArg("redisCounter");
//        constructorArgs.add(constructorArg);
//
//        BeanDefinition rateLimiterDefinition = new BeanDefinition("retaLimiter", "when.injection.RateLimiter");
//        rateLimiterDefinition.setLazyInit(false);
//        rateLimiterDefinition.setScope(Scope.PROTOTYPE);
//        rateLimiterDefinition.setConstructorArgs(constructorArgs);

        List<BeanDefinition.ConstructorArg> constructorArgsOfRedisCounter = new ArrayList<>();
        BeanDefinition.ConstructorArg integer = new BeanDefinition.ConstructorArg(true, int.class, "int");
        BeanDefinition.ConstructorArg string = new BeanDefinition.ConstructorArg(true, String.class, "string");
        constructorArgsOfRedisCounter.add(integer);
        constructorArgsOfRedisCounter.add(string);

        BeanDefinition redisCounterDefinition = new BeanDefinition("redisCounter", "when.injection.RedisCounter");
        redisCounterDefinition.setLazyInit(false);
        redisCounterDefinition.setScope(Scope.PROTOTYPE);
        redisCounterDefinition.setConstructorArgs(constructorArgsOfRedisCounter);

        BeanFactory factory = new BeanFactory();
        factory.addBeanDefinitions(Arrays.asList(redisCounterDefinition));

        RateLimiter bean = (RateLimiter) factory.createBean(redisCounterDefinition);
        bean.test();
    }

    @Test
    public void test() {

        BeanDefinition intBeanDefinition = new BeanDefinition("int", "int");
        intBeanDefinition.setLazyInit(false);
        intBeanDefinition.setScope(Scope.PROTOTYPE);
        intBeanDefinition.setConstructorArgs(Collections.emptyList());

        BeanDefinition.ConstructorArg integer = new BeanDefinition.ConstructorArg(true, int.class, "int");
        BeanDefinition testBeanDefinition = new BeanDefinition("test", "when.injection.Test");
        testBeanDefinition.setLazyInit(false);
        testBeanDefinition.setScope(Scope.PROTOTYPE);
        testBeanDefinition.setConstructorArgs(Arrays.asList(integer));

        BeanFactory factory = new BeanFactory();
        factory.addBeanDefinitions(Arrays.asList(intBeanDefinition, testBeanDefinition));

        Test test = (Test) factory.createBean(testBeanDefinition);
    }
}