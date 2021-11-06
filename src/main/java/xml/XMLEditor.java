package xml;

import Csv.Lanzador;
import Mapas.EstacionesMapas;
import Mapas.MagnitudMap;
import Mapas.UdMedidaMapa;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class XMLEditor {
    Document domExistente = new Document();
    Lanzador datos = new Lanzador();
    String uriInput = System.getProperty("user.dir")+ File.separator+"target"+File.separator+"generated-sources";
    MagnitudMap mm = MagnitudMap.getInstance();
    EstacionesMapas em = EstacionesMapas.getInstance();
    UdMedidaMapa umm = UdMedidaMapa.getInstance();
    String uri = System.getProperty("user.dir")+File.separator+"target"+File.separator+"db"+File.separator+"mediciones.xml";

    /**
     * editar el xml filtrado por el municipio
     * @param municipio por el que se filtrara
     */
    public void editarXML(String municipio){
        SAXBuilder sax = new SAXBuilder();
        Element rootCalidad = null;
        Element rootMeteo = null;
        try {
            domExistente = sax.build(uri);
            rootCalidad=sax.build(uriInput+File.separator+"datosCalidad.xml").getRootElement();
            rootMeteo=sax.build(uriInput+File.separator+"datosMeteo.xml").getRootElement();
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }

        Element rootE = domExistente.getRootElement();

        Document dom = new Document();
        Element root = new Element("datos");
        List<Element> elementosAniadir = rootE.getChildren("municipio");
        System.out.println(elementosAniadir.size());
        root.addContent(elementosAniadir);

        Element municipioElement = new Element("municipio");
        municipioElement.setAttribute("nombre",em.getCodigoMunicipio().get(Integer.parseInt(municipio)));
        Element calidad = new Element("calidad_aire");
        Element meteo = new Element("datos_meteorologicos");

        for (int i=0;i<432;i++) {
            if (i < 81 || i == 431) {
                Element elemento = crearHijo(rootCalidad, municipio, i);
                if (elemento != null) {
                    calidad.addContent(elemento);
                }
            }
            if (i >= 81 && i != 431) {
                Element elemento = crearHijo(rootMeteo, municipio, i);
                if (elemento != null) {
                    meteo.addContent(elemento);
                }
            }
        }

        municipioElement.addContent(calidad);
        municipioElement.addContent(meteo);
        root.addContent(municipioElement);


        dom.setRootElement(root);
        generateXML(dom);
    }

    /**
     * creacion de los nodos hijos ya filtrados y sacadas medias mensuales de todos los datos aportados por el xml de datos
     * @param rootElement root del xml de donde sacaremos los datos
     * @param municipio por el que filtrar
     * @param magnitud a ver
     * @return elemento filtrado
     */
    private Element crearHijo(Element rootElement, String municipio, int magnitud) {
        if(mm.getMapa().containsKey(magnitud)) {

            Element medicion = new Element(mm.getMapa().get(magnitud));
            Element maximo = new Element("maximo_mensual");
            Element minimo = new Element("minimo_mensual");
            Element media = new Element("media_mensual");
            Element fecha = new Element("fecha");

            List<Element> datos = rootElement.getChildren("datos");
            List<Double> maximas = new ArrayList<>();
            List<Double> minimos = new ArrayList<>();
            List<Double> medias = new ArrayList<>();
            String date = rootElement.getChild("datos").getChild("fecha").getText();

            datos.forEach(v -> {
                if (v.getChild("municipio").getText().equalsIgnoreCase(municipio) && v.getChild("magnitud").getText().equalsIgnoreCase(String.valueOf(magnitud))) {
                    maximas.add(Double.parseDouble(v.getChild("temp_max").getText()));
                    minimos.add(Double.parseDouble(v.getChild("temp_min").getText()));
                    medias.add(Double.parseDouble(v.getChild("temp_media").getText()));
                }
            });
            if(maximas.size()!=0) {
                Optional<Double> max = maximas.stream().max(Comparator.comparing(v -> v));
                maximo.setText(max.get()+" "+umm.getUdMedida().get(magnitud));
            }
            if(minimos.size()!=0) {
                Optional<Double> min = minimos.stream().min(Comparator.comparing(v -> v));
                minimo.setText(min.get()+" "+umm.getUdMedida().get(magnitud));
            }
            double med = (medias.stream().mapToDouble(v->v).sum())/ medias.size();
            media.setText(med+" "+umm.getUdMedida().get(magnitud));

            medicion.addContent(maximo);
            medicion.addContent(minimo);
            medicion.addContent(media);


            return medicion;
        }
        return null;
    }

    /**
     * creacion del xml creado
     * @param dom del xml a crear
     */
    private void generateXML(Document dom){
        XMLOutputter xml = new XMLOutputter(Format.getPrettyFormat());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(uri));
            xml.output(dom,bw);

            System.out.println("xml creado en la uri "+uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
