import Csv.Lanzador;
import org.jdom2.JDOMException;
import xml.DataXmlGenerator;
import xml.XmlCreator;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, JDOMException {

        /*if(args.length==2){
            Funcional funcional = Funcional.getInstance();
            funcional.start(args[0],args[1]);
        }
        else{
            System.out.println("valores aportados no validos, se busca: municipio uri");
        }*/
            Lanzador l = new Lanzador();
            l.empezar();
            DataXmlGenerator xml = DataXmlGenerator.getInstance(l.getListaCalidad(),l.getListaMeteo());
            XmlCreator xmlc = new XmlCreator();
            xmlc.crearXML("102");
            XmlCreator x1 = new XmlCreator();
            x1.loadData();
            x1.obtenerMediasMensuales().forEach(System.out::println);;
        }
    }

