package xml;

import Csv.Lanzador;
import Mapas.EstacionesMapas;
import Mapas.MagnitudMap;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class XmlCreator {

    Document dom = new Document();
    Lanzador datos = new Lanzador();
    String uriInput = System.getProperty("user.dir")+ File.separator+"target"+File.separator+"generated-sources";
    MagnitudMap mm = MagnitudMap.getInstance();
    EstacionesMapas em = EstacionesMapas.getInstance();

    public void crearXML(String municipio){
        Element root = new Element("datos");

        Element rootCalidad = null;
        Element rootMeteo = null;
        SAXBuilder sax = new SAXBuilder();
        try{
            rootCalidad=sax.build(uriInput+File.separator+"datosCalidad.xml").getRootElement();
            rootMeteo=sax.build(uriInput+File.separator+"datosMeteo.xml").getRootElement();
            //System.out.println(rootCalidad.getChild("datos").getChild("municipio").getText());
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
        }

        root.addContent(crearHijo(rootCalidad,municipio,"calidad_aire"));
        root.addContent(crearHijo(rootMeteo,municipio,"datos_meteorologicos"));

        dom.setRootElement(root);
        generateXML(dom);
    }

    private Element crearHijo(Element rootElement, String municipio,String nombre) {
        Element hijo = new Element(nombre);
        List<Element> datos = rootElement.getChildren("datos");
        datos.stream().filter(v->v.getChild("municipio").getText().equalsIgnoreCase(municipio)).
                forEach(v->{
                    Element magnitud = new Element(mm.getMapa().get(Integer.parseInt(v.getChild("magnitud").getText())));
                    magnitud.setAttribute(new Attribute("Municipio",em.getCodigoMunicipio().get(Integer.parseInt(municipio))));

                    Element max = new Element("temperatura_maxima");
                    max.setText(v.getChild("temp_max").getText());
                    Element min = new Element("temperatura_minima");
                    min.setText(v.getChild("temp_min").getText());
                    Element media = new Element("temperatura_media");
                    media.setText(v.getChild("temp_media").getText());
                    Element estacion = new Element("estacion");
                    estacion.setText(em.getCodigoMunicipio().get(v.getChild("estacion").getText()));
                    Element fecha = new Element("fecha");
                    fecha.setText(v.getChild("fecha").getText());

                    magnitud.addContent(max);
                    magnitud.addContent(min);
                    magnitud.addContent(media);
                    magnitud.addContent(estacion);
                    magnitud.addContent(fecha);

                    hijo.addContent(magnitud);
                });
        return hijo;
    }

    private void generateXML(Document dom){
        XMLOutputter xml = new XMLOutputter(Format.getPrettyFormat());

        String uri = System.getProperty("user.dir")+File.separator+"target"+File.separator+"db";
        File dir = new File(uri);
            if(!dir.exists()){
                dir.mkdirs();
            }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(uri+File.separator+"mediciones.xml",true));
            xml.output(dom,bw);

            System.out.println("xml creado en la uri "+uri+File.separator+"mediciones.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Lanzador l = new Lanzador();
        l.empezar();
        DataXmlGenerator xml = DataXmlGenerator.getInstance(l.getListaCalidad(),l.getListaMeteo());
        XmlCreator xmlc = new XmlCreator();
        xmlc.crearXML("102");
    }
}
