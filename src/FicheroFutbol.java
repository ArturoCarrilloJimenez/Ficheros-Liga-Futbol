import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

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
    public ArrayList<String[]> arrayLiga() {
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

    public int ordenarLiga() {
        int mensajeError = 0;
        ArrayList<String[]> arrayList = arrayLiga(); // Guardo en un array dinamico los datos de cada equipo

        if (arrayList != null) {
            int[] puntos = new int[arrayList.size()]; // Creo un array con el tamaño del array dinamico

           // Guardo los puntos de cada equipo en un array
            int posicionEquipo = 0;
            for (String[] datosEquipo : arrayList) {
                puntos[posicionEquipo] = Integer.parseInt(datosEquipo[5]);
                posicionEquipo++;
            }

            // Realiza la comprobacion de que los puntos, en caso de no estar ordenado canvia la posiocion el los dos arrays
            for (int j = 0;j < (puntos.length - 1);j++) {
                if (puntos[j] < puntos[j + 1]) {
                    // Canvio la posicion de los puntos
                    int puntuacionMenor = puntos[j];
                    puntos[j] = puntos[j + 1];

                    puntos[j + 1] = puntuacionMenor;

                    // Canvio la posicion de los datos del equipo
                    String[] datosEquipoMenor = arrayList.get(j);
                    
                    arrayList.set(j, arrayList.get(j + 1));
                    arrayList.set(j + 1, datosEquipoMenor);

                    j = -1; // Vuelvo a empezar el bucle (Este es -1 para que al sumarle 1 en el bucle se quede en 0)
                }
            } 
        }
        else {mensajeError = -1;}

        // Guardo los datos ordenados en el fichero
        if (mensajeError == 0) {
            rescribirFichero(arrayList);
        }

        return mensajeError;
    }

    private int borrarContenidoFichero() {
        int mensajeError = 0;
        try {
            FileWriter fileWriter = new FileWriter(NOMBRE_ARCHIVO);

            fileWriter.write("");

            fileWriter.close();
        } catch (Exception e) {
            mensajeError = -1;
        }

        return mensajeError;
    }

    private int rescribirFichero(ArrayList<String[]> arrayList) {
        int mensajeError = 0;

        // Borro el contenido del fichero
        mensajeError = borrarContenidoFichero();

        for (String[] datosEquipo : arrayList) {
            mensajeError = guardarEquipo(datosEquipo[0], Integer.parseInt(datosEquipo[1]), Integer.parseInt(datosEquipo[2]), Integer.parseInt(datosEquipo[3]), Integer.parseInt(datosEquipo[4]), Integer.parseInt(datosEquipo[5]));
        }

        return  mensajeError;
    }

    public String[] buscarEquipo(String nombreEquipo) {
        String[] equipoBuscado = null;
        ArrayList<String[]> arrayList = arrayLiga(); // Guardo en un array dinamico los datos de cada equipo

        if (arrayList != null) {
            for (String[] equipo : arrayList) {
                if (equipo[0].equals(nombreEquipo)) {
                    equipoBuscado = equipo;
                }
            }
        }

        return equipoBuscado;
    }

    public int borrarEquipo(String nombreEquipo) {
        int mensajeError = 0;
        ArrayList<String[]> arrayList = arrayLiga(); // Guardo en un array dinamico los datos de cada equipo

        if (arrayList  != null) {
            String[] equipo = buscarEquipo(nombreEquipo);

            if (equipo != null) {

                boolean centinela = false; // Valida que se alla borrado el equipo

                // Bucle que recorre el arrayList asta que se alla borrado el equipo
                for (int i = 0;centinela != true;i++) {
                    if (Arrays.equals(equipo, arrayList.get(i))) { // Cuando los dos  arrays son iguales es porque hemos encontrado al equipo a eliminar
                        arrayList.remove(i);
                        centinela = true;
                    }
                }

                mensajeError = rescribirFichero(arrayList);
            }
            else {mensajeError = -1;}
        }
        else {mensajeError = -1;}

        return mensajeError;
    }

    public int modificarEquipo(String nobreEquipo, int partJugados, int partGanados, int partEmpatados, int partPerdidos, int puntos){
        int mensajeError = 0;
        ArrayList<String[]> arrayList = arrayLiga(); // Guardo en un array dinamico los datos de cada equipo

        String[] equipoParaActualizar = buscarEquipo(nobreEquipo);

        boolean centinela = false;
        for (int i = 0;centinela != true &&  i < arrayList.size() ;i++ ) {
            if (Arrays.equals(equipoParaActualizar, arrayList.get(i))) {

                // Guarda los datos  del nuevo equipo en la posicion correspondiente
                equipoParaActualizar[1] = Integer.toString(partJugados);
                equipoParaActualizar[2] = Integer.toString(partGanados);
                equipoParaActualizar[3] = Integer.toString(partEmpatados);
                equipoParaActualizar[4] = Integer.toString(partPerdidos);
                equipoParaActualizar[5] = Integer.toString(puntos);

                // Rescribe el array con los datos actuales
                arrayList.set(i, equipoParaActualizar);

                centinela = true;
            }
        }

        mensajeError = rescribirFichero(arrayList);

        return mensajeError;
    }
}
