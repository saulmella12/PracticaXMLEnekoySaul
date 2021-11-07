package xml;

import Csv.Lanzador;
import Objetos.POJODatos;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
@Data
@XmlRootElement(name = "DatosMeteo")
@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbMeteo {
    List<POJODatos> listaMeteo = new ArrayList<>();
    public void cargarDatos() throws InterruptedException{
        Lanzador l = new Lanzador();
        l.empezar();
        listaMeteo= l.getListaMeteo();
    }
}
