package when.injection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: when
 * @create: 2020-03-02  11:44
 **/
public class XmlBeanConfigParser implements BeanConfigParser {

    @Override
    public List<BeanDefinition> parse(InputStream inputStream) {
        List beanDefinitions = new ArrayList<>();

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(inputStream);

            // TODO: read it later, 关于 xml 为什么需要 normalize 一下
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does
            // -it-work
            doc.getDocumentElement().normalize();

            NodeList beanList = doc.getElementsByTagName("bean");

            for (int i = 0; i < beanList.getLength(); i++) {
                Node node = beanList.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) continue;

                Element element = (Element) node;
                BeanDefinition beanDefinition = new BeanDefinition(
                        element.getAttribute("id"),
                        element.getAttribute("class")
                );
                if (element.getAttribute("scope").equals("singleton")) {
                    beanDefinition.setScope(Scope.SINGLETON);
                }
                if (element.getAttribute("lazy-init").equals("true")) {
                    beanDefinition.setLazyInit(true);
                }
                loadConstructorArgs(
                        element.getElementsByTagName("constructor-arg"),
                        beanDefinition
                );

                beanDefinitions.add(beanDefinition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return beanDefinitions;
    }

    public void loadConstructorArgs(NodeList nodes, BeanDefinition beanDefinition) {
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) continue;
            Element element = (Element) node;

            BeanDefinition.ConstructorArg constructorArg = null;
            if (!element.getAttribute("type").isEmpty()) {
                Object arg = element.getAttribute("value");
                constructorArg.setType(String.class);
                constructorArg = new BeanDefinition.ConstructorArg(false, String.class, arg);
            }

            if (!element.getAttribute("ref").isEmpty()) {
                Object arg = element.getAttribute("ref");
                constructorArg = new BeanDefinition.ConstructorArg(true, null, arg);
            }

            beanDefinition.addConstructorArg(constructorArg);
        }
    }

    //    @Override
//    public List<BeanDefinition> parse(InputStream inputStream) {
//        String content = "";
//        return parse(content);
//    }
//
//    @Override
    public List<BeanDefinition> parse(String configContent) {
        List<BeanDefinition> beanDefinitions = new ArrayList<>();
        // TODO: parse config content to bean definitions
        return beanDefinitions;
    }
}
