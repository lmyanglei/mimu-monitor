package cn.mimukeji.util.selector;

import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Selector class parses HTML/XML and finds elements that matches XPath query.
 * 
 * 
 * @author Talha Ashraf
 */


public class Selector {
    String text;
    Node root;

    public Selector(String text) {
        this.text = text;
        this.root = SelectorUtil.getNode(text);
    }

    public Selector(Node root) {
        this.text = SelectorUtil.nodeToString(root);
        this.root = root;
    }

    public SelectorList xpath(String xpath) {
        return new SelectorList(SelectorUtil.flatten(SelectorUtil.fromXpath(this.root, xpath)));
    }

    public Boolean bool(String xpath) {
        return (Boolean) SelectorUtil.fromXpath(this.root, xpath, XPathConstants.BOOLEAN);
    }

    public Node node(String xpath) {
        return (Node) SelectorUtil.fromXpath(this.root, xpath, XPathConstants.NODE);
    }

    public NodeList nodeset(String xpath) {
        return SelectorUtil.fromXpath(this.root, xpath);
    }

    public Double number(String xpath) {
        return (Double) SelectorUtil.fromXpath(this.root, xpath, XPathConstants.NUMBER);
    }

    public String string(String xpath) {
        return (String) SelectorUtil.fromXpath(this.root, xpath, XPathConstants.STRING);
    }

    @Override
    public String toString() {
        return String.format("<Selector name='%s'>", this.root.getNodeName());
    }
}

