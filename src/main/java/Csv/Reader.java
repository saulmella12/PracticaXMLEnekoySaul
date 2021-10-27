package Csv;

import lombok.Data;

import java.io.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class Reader {

    private String calidadCsv = System.getProperty("user.dir")+File.separator+"Datos"+File.separator+"calidad_aire_datos_mes.csv";
    //String meteoCsv = System.getProperty("user.dir")+File.separator+"Datos"+File.separator+"calidad_aire_datos_meteo_mes.csv";

    private static Reader reader = null;
    private Reader(){}

    public static Reader getInstance(){
        if(reader==null){
            reader = new Reader();
        }
        return reader;
    }

    public List<String> getAttributes() {
        List<String> returner =  null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(calidadCsv)));
            returner = Stream.of(br.readLine().split(";")).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returner;
    }

    /*public static void main(String[] args) {
        Reader r = new Reader();
        System.out.println(r.getAttributes());
    }*/
}
