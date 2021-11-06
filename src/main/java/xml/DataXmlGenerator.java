package xml;

import Objetos.POJODatos;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DataXmlGenerator {
    List<POJODatos> calidadList;
    List<POJODatos> meteoList;

    private static DataXmlGenerator xml = null;

    private DataXmlGenerator(List<POJODatos> calidadList, List<POJODatos> meteoList){
        this.meteoList=meteoList;
        this.calidadList=calidadList;
        generateXML();
    }

    public static DataXmlGenerator getInstance(List<POJODatos> calidadList, List<POJODatos> meteoList){
        if(xml==null){
            xml = new DataXmlGenerator(calidadList, meteoList);
        }
        return xml;
    }

    /**
     * creacion de xml a partir de dos listas de POJODatos, creando un dom por cada lista para crear un xml por cada lista
     */
    private void generateXML(){
        Document domCalidad = new Document();
        Document domMeteo = new Document();

        Element rootCalidad = new Element("calidad_del_aire");
        Element rootMeteo = new Element("datos_meteorologicos");

        calidadList.forEach(v->{
            rootCalidad.addContent(nodeGenerator(v));
        });
        meteoList.forEach(v->{
            rootMeteo.addContent(nodeGenerator(v));
        });

        domCalidad.setRootElement(rootCalidad);
        domMeteo.setRootElement(rootMeteo);

        outputXML(domCalidad,domMeteo);
    }

    /**
     * creacion de nodos a partir de un objeto POJODatos
     * @param datos POJODatos que usaremos para sacar la informacion a plasmar en el xml
     * @return el elemento creado
     */
    private Element nodeGenerator(POJODatos datos){
        Element child = new Element("datos");

        Element municipio = new Element("municipio");
        municipio.setText(datos.getMunicipio());
        Element estacion = new Element("estacion");
        estacion.setText(datos.getEstacion());
        Element magnitud = new Element("magnitud");
        magnitud.setText(datos.getMagnitud());
        Element fecha = new Element("fecha");
        fecha.setText(datos.getFecha());
        Element max = new Element("temp_max");
        max.setText(String.valueOf(datos.getMax()));
        Element min = new Element("temp_min");
        min.setText(String.valueOf(datos.getMin()));
        Element media = new Element("temp_media");
        media.setText(String.valueOf(datos.getMedia()));

        child.addContent(municipio);
        child.addContent(estacion);
        child.addContent(magnitud);
        child.addContent(fecha);
        child.addContent(max);
        child.addContent(min);
        child.addContent(media);

        return child;
    }

    /**
     * creacion de los xml a partir de los dom
     * @param domCalidad dom del xml calidad
     * @param domMeteo dom del xml datosMeteo
     */
    private void outputXML(Document domCalidad, Document domMeteo){
        String uri = System.getProperty("user.dir")+ File.separator+"target"+File.separator+"generated-sources";
        File file = new File(uri);

        if(!file.exists()){
            file.mkdirs();
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(uri+File.separator+"datosCalidad.xml")));
            XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
            output.output(domCalidad,bw);
            bw = new BufferedWriter(new FileWriter(new File(uri+File.separator+"datosMeteo.xml")));
            output.output(domMeteo,bw);

            System.out.println("xmls generados con exito en "+uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public static void main(String[] args) throws InterruptedException {
        Lanzador l = new Lanzador();
        l.empezar();
        DataXmlGenerator xml = DataXmlGenerator.getInstance(l.getListaCalidad(),l.getListaMeteo());
    }*/
}
