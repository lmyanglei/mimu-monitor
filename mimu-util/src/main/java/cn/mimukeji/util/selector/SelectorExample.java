package cn.mimukeji.util.selector;

public class SelectorExample {
    public static void main(String[] args) {
        String text = "<html><head><title>Selector List</title></head></html>";
        Selector selector = new Selector(text);
        SelectorList title = selector.xpath("//title/text()");
        // This will output "Selector List".
        System.out.println(title.extract()[0]);
    }
}