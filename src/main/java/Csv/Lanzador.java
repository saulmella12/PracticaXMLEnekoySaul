package Csv;

import Objetos.POJODatos;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Lanzador {
    List<POJODatos> listaCalidad = new ArrayList<>();
    List<POJODatos> listaMeteo = new ArrayList<>();
    ThreadGroup tg = new ThreadGroup("lectoresCSV");

    public void empezar() throws InterruptedException {
        CalidadReader car = CalidadReader.getInstance();
        MeteoReader dmr = MeteoReader.getInstance();

        Thread hilo1 = new Thread(tg,car);
        Thread hilo2 = new Thread(tg,dmr);


        hilo1.start();
        hilo2.start();
        hilo1.join();
        hilo2.join();

        listaCalidad = car.getListaCalidad();
        listaMeteo = dmr.getListaMeteo();

        // System.out.println(calidadList.size()+" "+calidadZonasList.size()+" "+datosList.size());
    }
}
