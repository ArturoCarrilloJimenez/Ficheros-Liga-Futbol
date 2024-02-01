import java.util.ArrayList;
import java.util.Scanner;

public class Interfaz {

    static Scanner scanner = new Scanner(System.in);
    static FicheroFutbol ficheroFutbol = new FicheroFutbol();

    public static void main(String[] args) {
        System.out.println("Bienvenido, que opcion deseas ejecutar");

        int opcion = -1;
        do {
            System.out.println("\nMenu de opciones");

            System.out.println("\n1. Introducir datos de un equipo");
            System.out.println("2. Mostrar datos");
            System.out.println("3. Ordenar datos");
            System.out.println("4. Buscar un equipo");
            System.out.println("5. Eliminar equipo");
            System.out.println("6. Modificar equipo");
            System.out.println("7. Salir del programa\n");

            System.out.println("Elige una de las opciones anteriores");
            opcion = introducirNumero();

            switch (opcion) {
                case 1:
                    introducirDatos();
                    break;
                case 2:
                    mostrarDatos();
                    break;
                case 3:
                    ordenarDatos();
                    break;
                case 4:
                    buscarEquipo();
                    break;
                case 5:
                    borrarEquipo();
                    break;
                case 6:
                    modificarEquipo();
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }

        } while (opcion != 7);

    }

    public static int introducirNumero() {
        int num = -1;

        boolean validInput = false;

        while (!validInput) {
            try {
                num = scanner.nextInt();
                validInput = true;
            } catch (Exception e) {
                System.out.println("Solo es posible introducir numeros");
                scanner.nextLine(); // Limpia el buffer del teclado
            }
        }

        return num;
    }

    // Opcion 1 Introducir datos de un equipo
    // Pregunta por el nombre de un equipo y despues los manda a la clase Fichero Futbol para que este lo guarde
    public static void introducirDatos() {

        if (ficheroFutbol.limiteEquipos() == false) {
            scanner.nextLine();

            System.out.println("\nIntroduce el nombre del equipo");
            String nombreEquipo;

            String[] busqueda = null;
            do {
                nombreEquipo = scanner.nextLine();

                busqueda = ficheroFutbol.buscarEquipo(nombreEquipo);

                if (nombreEquipo.length() > 20) {
                    System.out.println("No es posible introducir un nombre de equipo de mas de 20 carcteres");
                }
                else if (busqueda != null) {
                    System.out.println("Este equipo ya existe");
                }
            } while ((nombreEquipo.length() > 20) || (busqueda != null));
            
            // Metodo que pregunta el resto de datos
            int[] datosEquipo = datosEquipo();

            // Guarda los datos en el fichero
            int mensajeError = ficheroFutbol.guardarEquipo(nombreEquipo, datosEquipo[0], datosEquipo[1], datosEquipo[2], datosEquipo[3], datosEquipo[4]);

            // Resultado al guardar el fichero
            if (mensajeError == 0) {System.out.println("Se a guardado el equipo de forma correcta");}
            else {System.out.println("No se a podido guardar el equipo");}
        }
        else {System.out.println("Ya hay el maximo numero de equipos permitidos, para guardar un nuevo equipo deves de borrar uno existente");}
    }

    // Pregunta por todos los datos que de un equipo
    public static int[] datosEquipo() {
        System.out.println("Introduce los partidos que a jugado");
        int partJugados = -1;

        // Valida que no aya partidos negativos
        do {
            partJugados = introducirNumero();

            if (partJugados <= -1) {
                System.out.println("No puede haber numeros negativos");
            }
        } while (partJugados <= -1);

        System.out.println("Introduce los partidos que a ganado");
        int partGanados = -1;
        // Valida que no aya mas partidos ganados que jugados
        do {
            partGanados = introducirNumero();

            if (partGanados > partJugados) {
                System.out.println("No puede haber mas partidos ganados que jugados");
            }
            if (partGanados <= -1) {
                System.out.println("No puede haber numeros negativos");
            }
        } while ((partGanados > partJugados) || (partGanados <= -1));

        System.out.println("Introduce los partidos empatados");
        int partEmpatados = -1;

        // Valida que no aya mas partidos empatados que la resta de partidos jugados y ganados
        do {
            partEmpatados = introducirNumero();

            if (partEmpatados > (partJugados - partGanados)) {
                System.out.println("No puede haber mas partidos empatados que jugados y ganados");
            }
            if (partEmpatados <= -1) {
                System.out.println("No puede haber numeros negativos");
            }
        } while ((partEmpatados > (partJugados - partGanados)) || (partEmpatados <= -1));

        int partPerdidos = partJugados - (partGanados + partEmpatados);

        // Ganan 3 puntos cuando gana un partido, 1 al empatar y nada al perder
        int puntos = (partGanados * 3) + partEmpatados;

        int[] datos = {partJugados,partGanados,partEmpatados,partPerdidos,puntos};

        return datos;
    }

    // Opcion 2 Mostrar datos
    // Muestra todos los datos que estan guardados en el fichero
    // En caso de que el array sea null este muestra un mensaje de error
    public static void mostrarDatos() {
        ArrayList<String[]> datos = ficheroFutbol.arrayLiga();

        if (datos == null) {
            System.out.println("\nAlgo a fallado en la lectura de este");
        }
        else {
            System.out.printf("\n%-20s %10s %10s %10s %10s %10s\n", "Equipo", "Jugados", "Ganados", "Empatados", "Perdidos", "Puntos");
            for (String[] equipo : datos) {
                System.out.printf("%-20s %8s %10s %9s %10s %11s \n", equipo[0], equipo[1], equipo[2], equipo[3], equipo[4], equipo[5]);
            }
        }
    }

    // Opcion 3 Ordenar Datos
    // Ordena los equipos por sus puntos obtenidos
    public static void ordenarDatos() {
        System.out.println("\nDe que forma quieres ordenar la liga");
        System.out.println("1. Creciente");
        System.out.println("2. Decreciente\n");

        int opcion;

        do {
            opcion = introducirNumero();
            if (opcion <  1 || opcion > 2) {
                System.out.println("Opcion invalida");
            }
        } while (opcion <  1 || opcion > 2);

        int mensajeError = 0;
        
        if (opcion == 1) {mensajeError = ficheroFutbol.ordenarLiga(1);}
        else if(opcion == 2){mensajeError = ficheroFutbol.ordenarLiga(2);}

        if (mensajeError == 0) {System.out.println("Se a ordenado los datos de forma correcta");}
        else {System.out.println("No se a podido ordenar los equipos");}
    }

    // Opcion 4 Buscar equipo
    // Pide al usuario el nombre del equipo que quiere buscar y en caso de que exista mustra los datos
    public static void  buscarEquipo() {
        System.out.println("\nQue equipo  deseas buscar");
        scanner.nextLine();
        String nombreBuscado = scanner.nextLine();

        String[] equipo = ficheroFutbol.buscarEquipo(nombreBuscado);

        if (equipo != null) {
            System.out.printf("\n%-20s %10s %10s %10s %10s %10s\n", "Equipo", "Jugados", "Ganados", "Empatados", "Perdidos", "Puntos");
            System.out.printf("%-20s %8s %10s %9s %10s %11s \n", equipo[0], equipo[1], equipo[2], equipo[3], equipo[4], equipo[5]);
        }
        else {System.out.println("No se a encontrado el equipo " + nombreBuscado);}
    }

    // Opcion  5 Borrar Equipos
    // Pide al usuario el nombre del equipo que quiere borrar, si existe lo elimina deli fichero
    public static void borrarEquipo() {
        System.out.println("\nQue equipo  deseas eliminar");
        scanner.nextLine();
        String nombreBuscado = scanner.nextLine();

        int mensajeError = ficheroFutbol.borrarEquipo(nombreBuscado);

        if (mensajeError == 0) {System.out.println("Se ha eliminado el equipo " + nombreBuscado + " correctamente");}
        else {System.out.println("No a sido posible eliminar el equipo" + nombreBuscado);}
    }

    // Opcion 6 Modificar equipo
    // Pide al usuario el nombre del equipo que quiere modificar y si existe mustra los datos actuales por pantalla
    // Una vez mostrados pide nuevos datos para este equipo y sustitulle los datos antiguos por los actuales
    public static void modificarEquipo() {
        System.out.println("\nQue equipo  deseas modificar");
        scanner.nextLine();
        String nombreBuscado = scanner.nextLine();

        String[] equipo = ficheroFutbol.buscarEquipo(nombreBuscado);

        if (equipo != null) {
            System.out.printf("\n%-20s %10s %10s %10s %10s %10s\n", "Equipo", "Jugados", "Ganados", "Empatados", "Perdidos", "Puntos");
            System.out.printf("%-20s %8s %10s %9s %10s %11s \n", equipo[0], equipo[1], equipo[2], equipo[3], equipo[4], equipo[5]);

            int[] datosActuales = datosEquipo();

            int mensajeError = ficheroFutbol.modificarEquipo(nombreBuscado, datosActuales[0], datosActuales[1], datosActuales[2], datosActuales[3], datosActuales[4]);

            if (mensajeError == 0) {System.out.println("Se ha modificado el equipo " + nombreBuscado + " correctamente");}
            else {System.out.println("No a sido posible modificar el equipo" + nombreBuscado);}
        }
        else {System.out.println("No se a encontrado el equipo " + nombreBuscado);}
    }
}
