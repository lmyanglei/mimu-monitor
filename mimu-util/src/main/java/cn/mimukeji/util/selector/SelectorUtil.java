package cn.mimukeji.util.selector;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Basic utility functions used within the project.
 *
 *
 * @author Talha Ashraf
 */


public class SelectorUtil {
    private static final Logger LOG = Logger.getLogger("io.parsel.Utils");
    private static final XPath XPATH = XPathFactory.newInstance().newXPath();

    public static DocumentBuilder getDocumentBuilder() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch(ParserConfigurationException e) {
            LOG.severe(e.getMessage());
        }
        return null;
    }

    public static Node getNode(String text) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(text.getBytes());
            return (Node) getDocumentBuilder().parse(bais);
        } catch(IOException|SAXException e) {
            LOG.severe(e.getMessage());
        }
        return null;
    }

    public static String nodeToString(Node node) {
        StringWriter stringWriter = new StringWriter();
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(node), new StreamResult(stringWriter));
        } catch (TransformerException e) {
            LOG.severe(e.getMessage());
        }
        return stringWriter.toString();
    }

    public static NodeList fromXpath(Node node, String xpath) {
        return (NodeList) fromXpath(node, xpath, XPathConstants.NODESET);
    }

    public static Object fromXpath(Node node, String xpath, QName type) {
        try {
            return XPATH.evaluate(xpath, node, type);
        } catch(XPathExpressionException e) {
            LOG.severe(e.getMessage());
        }
        return null;
    }

    public static ArrayList<Selector> flatten(NodeList nodeList) {
        ArrayList<Selector> listOfSelector = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            listOfSelector.add(new Selector(nodeList.item(i)));
        }
        return listOfSelector;
    }

    public static String xPercentString(String text, int percent) {
        int index = (text.length() * percent) / 100;
        String remove_text = text.substring(index);
        return text.replace(remove_text, "");
    }
}

