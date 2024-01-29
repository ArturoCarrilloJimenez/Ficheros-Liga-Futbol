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
                    
                    break;
                case 5:
                    
                    break;
                case 6:
                    
                    break;
                case 7:
                    
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
    // Pregunta todos los datos que necesita y despues los mada a la clase Fichero Futbol para que este lo guarde
    public static void introducirDatos() {

        scanner.nextLine();

        System.out.println("\nIntroduce el nombre del equipo");
        String nombreEquipo;

        do {
            nombreEquipo = scanner.nextLine();

            if (nombreEquipo.length() > 20) {
                System.out.println("No es posible introducir un nombre de equipo de mas de 20 carcteres");
            }
        } while (nombreEquipo.length() > 20);

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

        // Guarda los datos en el fichero
        int mensajeError = ficheroFutbol.guardarEquipo(nombreEquipo, partJugados, partGanados, partEmpatados, partPerdidos, puntos);

        // Resultado al guardar el fichero
        if (mensajeError == 0) {System.out.println("Se a guardado el equipo de forma correcta");}
        else {System.out.println("No se a podido guardar el equipo");}
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
            System.out.println(String.format("\n%-20s %10s %10s %10s %10s %10s", "Equipo", "Jugados", "Ganados", "Empatados", "Perdidos", "Puntos"));
            for (String[] equipo : datos) {
                System.out.println(String.format("%-20s %8s %10s %9s %10s %11s", equipo[0], equipo[1], equipo[2], equipo[3], equipo[4], equipo[5]));
            }
        }
    }

    public static void ordenarDatos() {
        int mensajeError = ficheroFutbol.ordenarLiga();

        if (mensajeError == 0) {System.out.println("\nSe a ordenado los datos de forma correcta");}
        else {System.out.println("\nNo se a podido ordenar los equipos");}
    }
}
