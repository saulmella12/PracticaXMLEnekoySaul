import Mapas.EstacionesMapas;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Funcional {

    private String codMunicipio=null;

    private static Funcional funcional = null;
    private Funcional() {}
    public static Funcional getInstance() {
        if (funcional == null) {
            funcional = new Funcional();
        }
        return funcional;
    }

    /**
     * a partir de el codigo de municipio y la uri de guardado del html generamos este
     *
     * @param municipio es el codigo del municipio que queremos buscar
     * @param uri       la direccion donde guardaremos el html
     * @throws IOException si el generador de imagenes tiene problemas para guardar la imagen
     */
    public void start(String municipio, String uri) throws IOException, InterruptedException {
        Long startTime = System.currentTimeMillis();
        EstacionesMapas em = EstacionesMapas.getInstance();

        checkFile(uri,municipio);
        municipioExists(municipio);
        crearCarpeta(uri);
        if (codMunicipio==null){
            System.out.println("municipio no encontrado");
        }
        else {
            //inicio del programa
        }

        ejecutarHtml(uri,municipio);

    }

    /**
     * vemos si existe el municipio pasado por parametro y si existe guardamos la el codigo asocuiado a este
     * @param municipio del parametro
     */
    private void municipioExists(String municipio) {
        EstacionesMapas em = EstacionesMapas.getInstance();
        if (em.getCodigoMunicipio().containsValue(municipio)) {
            for (Map.Entry<Integer, String> entry : em.getCodigoMunicipio().entrySet()) {
                if (Objects.equals(entry.getValue(), municipio)) {
                    codMunicipio = String.valueOf(entry.getKey());
                }
            }
        }
    }

    /**
     * miramos si existe la carpeta solicitada para guardar el html, y, si nok existe, la creamos
     * @param uri
     */
    private void crearCarpeta(String uri){
        File dir = new File(uri);
        if (!dir.exists()){
            dir.mkdirs();
        }
    }

    /**
     * miramos si el archivo ya exste, y preguntamos si existe preguntamos si desea sobreescribirlo, en caso negativo se cierra el problema
     * @param uri donde estaria el fichero
     * @param municipio nombre del municipio
     */
    private void checkFile(String uri,String municipio){
        File file = new File(uri+File.separator+municipio+".html");
        if(file.exists()){
            System.out.println("parece que ya existe el archivo");
            System.out.println("quiere reemplazar el archivo existente? si/no");
            Scanner sc = new Scanner(System.in);
            String ans = sc.next();
            if(ans.equalsIgnoreCase("no")){
                System.out.println("proceda a sacar de la carpeta el archivo");
                System.exit(0);
            }
        }
    }

    /**
     * ejecurtamos el html una vez se ha generado
     * @param uri localizacion del archivo
     * @param municipio nombre del municipio asociado al archivo
     */
    private void ejecutarHtml(String uri, String municipio){
        String urii = uri+File.separator+municipio+".html";
        File htmlFile = new File(urii);
        try {
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
