package Objetos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor

public class DatosMeteorologicos {
    String municipio,estacion,magnitud,fecha;
    List<Double> temperaturas;
    double max,min,media;
}
