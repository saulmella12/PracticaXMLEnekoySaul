package Csv;

import Objetos.POJODatos;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Lanzador {
    List<POJODatos> listaCalidad = new ArrayList<>();
    List<POJODatos> listaMeteo = new ArrayList<>();


}
