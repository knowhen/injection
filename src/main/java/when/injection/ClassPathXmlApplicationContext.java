package when.injection;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author: when
 * @create: 2020-03-02  11:36
 **/
public class ClassPathXmlApplicationContext implements ApplicationContext {
    private BeanFactory beanFactory;
    private BeanConfigParser configParser;

    public ClassPathXmlApplicationContext(String configLocation) {
        this.beanFactory = new BeanFactory();
        this.configParser = new XmlBeanConfigParser();
        loadBeanDefinitions(configLocation);
    }

    private void loadBeanDefinitions(String configLocation) {
        InputStream inputStream = null;
        try {
            inputStream = this.getClass().getResourceAsStream("/" + configLocation);
            if (inputStream == null) {
                throw new RuntimeException("Can not find config file: " + configLocation);
            }
            List<BeanDefinition> beanDefinitions = configParser.parse(inputStream);
            beanFactory.addBeanDefinitions(beanDefinitions);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                // TODO: log error
            }
        }
    }

    @Override
    public Object getBean(String beanId) {
        return beanFactory.getBean(beanId);
    }
}
