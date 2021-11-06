package xml;

import Csv.Lanzador;

import java.io.File;

public class OutputXMLManager {
    String uri = System.getProperty("user.dir")+ File.separator+"target"+File.separator+"db"+File.separator+"mediciones.xml";
    XmlCreator creator = new XmlCreator();
    XMLEditor editor = new XMLEditor();

    /**
     * mira que el archivo xml no exista, si existe llama a editar xml, si no existe, llama a crear xml
     * @param municipio al que se pide
     */
    public void checkFile(String municipio){
        File xml = new File(uri);
        if(xml.exists()){
            editor.editarXML(municipio);
        }
        else{
            creator.crearXML(municipio);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Lanzador l = new Lanzador();
        l.empezar();
        DataXmlGenerator xml = DataXmlGenerator.getInstance(l.getListaCalidad(),l.getListaMeteo());
        OutputXMLManager manager = new OutputXMLManager();
        manager.checkFile("102");
    }
}
