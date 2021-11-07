package markDown;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import xpath.*;

public class MDCreator {

    public void mdCreator(String uri, String municipio){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(uri));) {
            XpathManager manager = new XpathManager();
            bw.write("## Datos de "+municipio+"\n");
            manager.obtenerMediasMensuales().forEach(v->{
                try {
                    bw.write("* "+v+"\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
