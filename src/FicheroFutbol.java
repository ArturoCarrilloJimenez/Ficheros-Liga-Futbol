import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class FicheroFutbol {
    
    // Guardo en una costante el nombre del archivo
    private static final String NOMBRE_ARCHIVO = "MiLiga.txt";
    private static final int  MAXIMO_EQUIPOS = 15;

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

    // Metodo que comprueba si se a llegado o no al limite de equipos
    public boolean limiteEquipos() {
        boolean limite = false;
        ArrayList<String[]> arrayList = arrayLiga(); // Guardo en un array dinamico los datos de cada equipo
        int i = 0;

        if (arrayList != null) {
            for (int j = 0;j < arrayList.size();j++) {
                i++;
            }
        }

        if (i >=  MAXIMO_EQUIPOS) {
            limite = true;      
        }

        return limite;
    }

    // Este metodo guarda en un arrayList (Array dinamico) un array de String con los datos de cada equipo
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

    // Metodo que ordena el fichero
    public int ordenarLiga(int tipo) {
        int mensajeError = 0;
        ArrayList<String[]> arrayList = arrayLiga(); // Guardo en un array dinamico los datos de cada equipo

        if (arrayList != null) {
            int[] puntos = new int[arrayList.size()]; // Creo un array de puntos con el tamaño del array dinamico
            int[] partGanador = new int[arrayList.size()]; // Creo un array de puntos con el tamaño del array dinamico

           // Guardo los puntos de cada equipo en un array
            int posicionEquipo = 0;
            for (String[] datosEquipo : arrayList) {
                puntos[posicionEquipo] = Integer.parseInt(datosEquipo[5]);
                partGanador[posicionEquipo] = Integer.parseInt(datosEquipo[2]);
                posicionEquipo++;
            }

             if (tipo == 1) {arrayList = ordenarCrecientemente(arrayList, puntos, partGanador);}
             else if (tipo == 2) {arrayList = ordenarDecreciente(arrayList, puntos, partGanador);}
        }
        else {mensajeError = -1;}

        // Guardo los datos ordenados en el fichero
        if (mensajeError == 0) {
            rescribirFichero(arrayList);
        }

        return mensajeError;
    }

    // Metodo que realiza la comparacion de ordenacion creciente por puntos y goles a favor
    private int compararOrdenacionCreciente(int puntos[], int[] partGanados, int posicion) {
        int comparacion = 0;
        
        if (puntos[posicion] < puntos[posicion + 1]) {comparacion = -1;}
        else if ((puntos[posicion] == puntos[posicion + 1]) && (partGanados[posicion] < partGanados[posicion + 1])) {comparacion = -1;}

        return comparacion;
    }

    // Realiza la ordenacion creciente con el metodo de la burbuja
    private ArrayList<String[]> ordenarCrecientemente(ArrayList<String[]> arrayList, int[] puntos, int[] partGanados) {
        // Realiza la comprobacion de que los puntos, en caso de no estar ordenado canvia la posiocion el los dos arrays
        for (int j = 0;j < (puntos.length - 1);j++) {
            int comparacion  = compararOrdenacionCreciente(puntos, partGanados, j);

            if (comparacion == -1) {
                // Canvio la posicion de los puntos
                int puntuacionMenor = puntos[j];
                puntos[j] = puntos[j + 1];

                puntos[j + 1] = puntuacionMenor;

                // Canvio la posicion de los partidos ganados
                int partGanadorMenor = partGanados[j];
                partGanados[j] = partGanados[j + 1];

                partGanados[j + 1] = partGanadorMenor;

                // Canvio la posicion de los datos del equipo
                String[] datosEquipoMenor = arrayList.get(j);
                
                arrayList.set(j, arrayList.get(j + 1));
                arrayList.set(j + 1, datosEquipoMenor);

                j = -1; // Vuelvo a empezar el bucle (Este es -1 para que al sumarle 1 en el bucle se quede en 0)
            }
        }

        return  arrayList;
    }

    // Metodo que realiza la comparacion de ordenacion decreciente por puntos y goles a favor
    private int compararOrdenacionDecreciente(int puntos[], int[] partGanados, int posicion) {
        int comparacion = 0;
        
        if (puntos[posicion + 1] < puntos[posicion]) {comparacion = -1;}
        else if ((puntos[posicion + 1] == puntos[posicion]) && (partGanados[posicion + 1] < partGanados[posicion])) {comparacion = -1;}

        return comparacion;
    }

    // Realiza la ordenacion decreciente con el metodo de la burbuja
    private ArrayList<String[]> ordenarDecreciente(ArrayList<String[]> arrayList, int[] puntos, int[] partGanados) {
        // Realiza la comprobacion de que los puntos, en caso de no estar ordenado canvia la posiocion el los dos arrays
        for (int j = 0;j < (puntos.length - 1);j++) {
            int comparacion = compararOrdenacionDecreciente(puntos, partGanados, j);

            if (comparacion == -1) {
                // Canvio la posicion de los puntos
                int puntuacionMenor = puntos[j + 1];
                puntos[j + 1] = puntos[j];

                puntos[j] = puntuacionMenor;

                // Canvio la posicion de los partidos ganados
                int partGanadorMenor = partGanados[j + 1];
                partGanados[j + 1] = partGanados[j];

                partGanados[j] = partGanadorMenor;

                // Canvio la posicion de los datos del equipo
                String[] datosEquipoMenor = arrayList.get(j + 1);
                
                arrayList.set(j + 1, arrayList.get(j));
                arrayList.set(j, datosEquipoMenor);

                j = -1; // Vuelvo a empezar el bucle (Este es -1 para que al sumarle 1 en el bucle se quede en 0)
            }
        }
        return  arrayList;
    }

    // Borra todo el contenido del fichero
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

    // Metodo que rescribe el fichero
    private int rescribirFichero(ArrayList<String[]> arrayList) {
        int mensajeError = 0;

        // Borro el contenido del fichero
        mensajeError = borrarContenidoFichero();

        for (String[] datosEquipo : arrayList) {
            mensajeError = guardarEquipo(datosEquipo[0], Integer.parseInt(datosEquipo[1]), Integer.parseInt(datosEquipo[2]), Integer.parseInt(datosEquipo[3]), Integer.parseInt(datosEquipo[4]), Integer.parseInt(datosEquipo[5]));
        }

        return  mensajeError;
    }

    // Metodo que realiza una busqueda del nombre del equipo
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

    // Metodo que elimina un equipo del fichero
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

    // Metodo que rescribe un equipo con datos nuevos
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
