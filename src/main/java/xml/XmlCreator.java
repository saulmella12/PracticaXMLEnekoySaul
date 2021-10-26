package xml;

import org.jdom2.Document;
import org.jdom2.Element;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XmlCreator {

    public Document createElement(String name, String content, Document dom){
        Element rootElement = dom.getRootElement();
        Element child = new Element(name);
        child.addContent(content);

        rootElement.addContent(child);
        dom.setRootElement(rootElement);
        return dom;
    }

    public void createXML(List<String> attribute,String csvUri, String xmlName) throws IOException {
        Element rootElement = new Element(xmlName);
        Document dom = new Document();
        dom.setRootElement(rootElement);

        LineNumberReader lines = new LineNumberReader(new FileReader(new File(csvUri)));
        BufferedReader reader = new BufferedReader(new FileReader(new File(csvUri)));
        reader.readLine();
        for(int i = 0; i<lines.getLineNumber();i++){
            List<String> values = Stream.of(reader.readLine().split(";")).collect(Collectors.toList());
            dom = elementsCreator(attribute,values,dom);
        }
    }

    private Document elementsCreator(List<String> attributes, List<String> values, Document dom){
        Element rootElement = dom.getRootElement();
        Document domReturner = null;
        for(int i=0;i<attributes.size();i++){
            domReturner = createElement(attributes.get(i),values.get(i),dom);
        }
        return domReturner;
    }
}
