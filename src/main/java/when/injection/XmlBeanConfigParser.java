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

            ConstructorArg constructorArg;
            boolean isReferenceType = element.hasAttribute("ref");
            if (isReferenceType) {
                constructorArg = new ConstructorArg.Builder(true)
                        .setArg(element.getAttribute("ref"))
                        .build();
            } else {
                constructorArg = new ConstructorArg.Builder(false)
                        .setType(String.class)
                        .setArg(element.getAttribute("value"))
                        .build();
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
