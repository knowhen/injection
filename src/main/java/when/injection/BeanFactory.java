package when.injection;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: when
 * @create: 2020-03-02  11:49
 **/
public class BeanFactory {
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionList = new ConcurrentHashMap<>();

    public void addBeanDefinitions(List<BeanDefinition> beanDefinitions) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            this.beanDefinitionList.putIfAbsent(beanDefinition.getId(), beanDefinition);
        }

        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (beanDefinition.isLazyInit() == false && beanDefinition.isSingleton()) {
                createBean(beanDefinition);
            }
        }
    }

    public Object getBean(String beanId) {
        BeanDefinition beanDefinition = beanDefinitionList.get(beanId);
        if (beanDefinition == null) {
            throw new NoSuchBeanDefinitionException("Bean is not define:" + beanId);
        }
        return createBean(beanDefinition);
    }

    //@VisibleForTesting
    protected Object createBean(BeanDefinition beanDefinition) {
        if (beanDefinition.isSingleton() && singletonObjects.contains(beanDefinition)) {
            return singletonObjects.get(beanDefinition);
        }

        Object bean = null;
        try {
            Class beanClass = Class.forName(beanDefinition.getClassName());
            List<ConstructorArg> args = beanDefinition.getConstructorArgs();
            if (args.isEmpty()) {
                bean = beanClass.newInstance();
            } else {
                Class[] argClasses = new Class[args.size()];
                Object[] argObjects = new Object[args.size()];
                for (int i = 0; i < args.size(); i++) {
                    ConstructorArg arg = args.get(i);
                    if (!arg.isReferenceType()) {
                        argClasses[i] = arg.getType();
                        argObjects[i] = arg.getArg();
                    } else {
                        BeanDefinition refBeanDefinition = beanDefinitionList.get(arg.getArg());
                        if (refBeanDefinition == null) {
                            throw new NoSuchBeanDefinitionException("Bean is not define:" + arg.getArg());
                        }
                        argClasses[i] = Class.forName(refBeanDefinition.getClassName());
                        argObjects[i] = createBean(refBeanDefinition);
                    }
                }
                bean = beanClass.getConstructor(argClasses).newInstance(argObjects);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new BeanCreationFailedException("", e);
        }
        if (bean != null && beanDefinition.isSingleton()) {
            singletonObjects.putIfAbsent(beanDefinition.getId(), bean);
            return singletonObjects.get(beanDefinition.getId());
        }
        return bean;
    }
}
