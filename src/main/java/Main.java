import Csv.CalidadReader;
import Csv.Lanzador;
import Csv.MeteoReader;
import Objetos.POJODatos;
import org.jdom2.JDOMException;
import xml.DataXmlGenerator;
import xml.GenerarXmlCalidad;
import xml.JaxbCalidad;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, JDOMException, JAXBException {

        /*if(args.length==2){
            Funcional funcional = Funcional.getInstance();
            funcional.start(args[0],args[1]);
        }
        else{
            System.out.println("valores aportados no validos, se busca: municipio uri");
        }*/
            /*Lanzador l = new Lanzador();
            l.empezar();
            DataXmlGenerator xml = DataXmlGenerator.getInstance(l.getListaCalidad(),l.getListaMeteo());*/
            /*XmlCreator xmlc = new XmlCreator();
            xmlc.crearXML("102");
            XmlCreator x1 = new XmlCreator();
            x1.loadData();
            x1.obtenerMediasMensuales().forEach(System.out::println);;*/
            CalidadReader car = CalidadReader.getInstance();
            MeteoReader dmr = MeteoReader.getInstance();
            POJODatos pd = new POJODatos();
            JaxbCalidad jc = new JaxbCalidad();
            GenerarXmlCalidad gx = new GenerarXmlCalidad();
            gx.generarXmlCalidad();
            gx.generarXmlMeteo();

        }
    }

