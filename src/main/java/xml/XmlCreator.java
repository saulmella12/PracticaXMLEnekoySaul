package xml;

import Csv.Lanzador;
import Objetos.POJODatos;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class XmlCreator {

    Document dom = new Document();
    Lanzador datos = new Lanzador();

    public void crearXML(String uri, String municipio){
        Element root = new Element("datos");

        root.addContent(crearHijo(datos.getListaCalidad(),municipio,"calidad_aire"));
        root.addContent(crearHijo(datos.getListaMeteo(),municipio,"datos_meteorologicos"));

        dom.setRootElement(root);
        generateXML(uri,dom);
    }

    private Element crearHijo(List<POJODatos> lista, String municipio,String nombre) {
        Element hijo = new Element(nombre);
        lista.forEach(v->{
            if(v.getMunicipio().equalsIgnoreCase(municipio)){
                Element nodoHijo = new Element(v.getMagnitud());
                Element media = new Element("media_mensual");
                media.setText(String.valueOf(v.getMedia()));
                Element maximo = new Element("maximo");
                media.setText(String.valueOf(v.getMax()));
                Element minimo = new Element("minimo");
                media.setText(String.valueOf(v.getMin()));
                Element fecha = new Element("fecha");
                media.setText(v.getFecha());

                nodoHijo.setContent(media);
                nodoHijo.setContent(maximo);
                nodoHijo.setContent(minimo);
                nodoHijo.setContent(fecha);

                hijo.addContent(nodoHijo);
            }
        });
        return hijo;
    }

    private void generateXML(String uri, Document dom){
        XMLOutputter xml = new XMLOutputter();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(uri));
            xml.output(dom,bw);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
