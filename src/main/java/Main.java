import Csv.Reader;
import xml.XmlCreator;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        /*System.out.println("Hola Mundo");
        System.out.println("Adios Mundo");*/
        Reader reader = Reader.getInstance();
        XmlCreator xml = new XmlCreator();

        try {
            xml.createXML(reader.getAttributes(),reader.getCalidadCsv(),"calidadAire");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
