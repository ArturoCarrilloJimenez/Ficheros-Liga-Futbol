import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FicheroFutbol {
    
    // Guardo en una costante el nombre del archivo
    private static final String NOMBRE_ARCHIVO = "MiLiga.txt";

    // Metodo que gaurda en el fichero los datos de un equipo
    public int guardarEquipo(String nobreEquipo, int partJugados, int partGanados, int partEmpatados, int partPerdidos, int puntos) {
        int mensajeError = 0;
        try {
            FileWriter fileWriter = new FileWriter(NOMBRE_ARCHIVO, true);

            fileWriter.write(nobreEquipo + ";" + partJugados + ";" + partGanados + ";" + partEmpatados + ";" + partPerdidos + ";" + puntos + "\n");

            fileWriter.close();
        } catch (Exception e) {
            mensajeError = -1;
        }

        return mensajeError;
    }

    // Este metodo guarda en un arrayList (Array dinamico) un arrayd con los datos de cada equipo
    public ArrayList<String[]> mostrarLiga() {
        ArrayList<String[]> arrayList = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(NOMBRE_ARCHIVO);
            BufferedReader bufferedReader = new BufferedReader(fileReader); // Creo esta clase para poder leer lieas de texto en vez de ir de bit en bit
            
            String linea = "";
            String[] datosEquipo;

            // Bucle que lee el texto y lo guarda de una forma mas optima para su lectura
            while ((linea = bufferedReader.readLine()) != null) {
                datosEquipo = linea.split(";"); // Guardo en el array las fila de cada equipo

                // Añado los datos del equipo
                arrayList.add(datosEquipo);
            }

            // Cierro los abjetos que e utilizado al realizar la lectura
            bufferedReader.close();
            fileReader.close();
        } catch (Exception e) {
            arrayList = null; // En caso de fallo este devuelve un null
        }

        return arrayList;
    }
}
