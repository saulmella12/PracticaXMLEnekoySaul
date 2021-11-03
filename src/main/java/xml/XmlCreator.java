package xml;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XmlCreator {

   /* public void createXML(List<String> attribute,String csvUri, String xmlName) throws IOException {
        Element rootElement = new Element(xmlName);
        Document dom = new Document();
        RandomStringGenerator r = RandomStringGenerator.getInstance();

        Long maxLines = Files.lines(Path.of(csvUri), Charset.forName("windows-1252")).count();
        System.out.println(maxLines);
        System.out.println(attribute.size());
        BufferedReader reader = new BufferedReader(new FileReader(new File(csvUri)));


        reader.readLine();
        String line;
        while((line=reader.readLine()) != null) {
            List<String> values = Stream.of(line.split(";")).collect(Collectors.toList());
            rootElement = elementsCreator(attribute, values, dom, r.randomString(), rootElement);
        }

        *//*System.out.println(rootElement);*//*
        dom.setRootElement(rootElement);

        xmlGenerator(dom,xmlName);
    }

    private Element elementsCreator(List<String> attributes, List<String> values, Document dom, String parent, Element root){
        Element rootElement = root;
        Element rootChild = new Element(parent);

        for(int i=0;i<attributes.size();i++){
            if(i>3) {
                 rootChild.addContent(createElement(attributes.get(i), values.get(i), dom));
            }
        }
        rootElement.addContent(rootChild);
        return rootElement;
    }

    private Element createElement(String name, String content, Document dom){
        Element child = new Element(name);
        child.addContent(content);

        return child;
    }

    private void xmlGenerator(Document dom, String name){
        XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
        String xmlPath = System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources";
        File xml = new File(xmlPath);
        if(!xml.exists()){
            xml.mkdirs();
        }
        xml= new File(xmlPath+File.separator+name+".xml");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(xml));
            xmlOutput.output(dom,bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
