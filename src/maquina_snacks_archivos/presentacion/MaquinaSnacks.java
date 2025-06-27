package maquina_snacks_archivos.presentacion;

import maquina_snacks_archivos.dominio.Snack;
import maquina_snacks_archivos.servicio.IServicioSnacks;
import maquina_snacks_archivos.servicio.ServicioSnacksArchivos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MaquinaSnacks {
    public static void main(String[] args) {
        iniciarMaquinaSnacks();
    }

    public static void iniciarMaquinaSnacks() {
        boolean salir = false;
        Scanner sc = new Scanner(System.in);
        //Crear objeto de tipo Servicio snacks
        IServicioSnacks servicioSnacks = new ServicioSnacksArchivos();
        // Lista de Snacks que se van comprando
        List<Snack> snacksComprados = new ArrayList<>();
        int opcion = 0;
        do {
            System.out.println("""
                --------------------------------------------
                ******       Maquina de snacks       ******
                --------------------------------------------""");
            servicioSnacks.mostrarSnacks(); //mostrar todo el inventario
            opcion = ingresarOpcion(sc);
            ejecutarOpcion(opcion, sc, snacksComprados, servicioSnacks);
            if(opcion == 4) {
                salir = true;
            }
        } while(!salir);
    }

    public static void crearSnack(Scanner sc, IServicioSnacks servicioSnacks) {
        String nombreSnack;
        boolean nombreValido;
        do {
            nombreValido = true;
            System.out.print("Ingresa el nombre del snack: ");
            nombreSnack = sc.nextLine().trim();
            if(nombreSnack.isEmpty()) {
                System.out.println("*** Error, el nombre esta vacío ***\n");
                nombreValido = false;
            } else if(nombreSnack.length() < 3) {
                System.out.println("*** Error, el nombre del snack debe contener al menos 3 caracteres ***\n");
                nombreValido = false;
            }
        } while(!nombreValido);

        boolean precioValido;
        double precioSnack = 0.0;
        do {
            precioValido = true;
            System.out.print("Ingresa el precio del snack: ");
            try {
                precioSnack = Double.parseDouble(sc.nextLine().trim());
                if(precioSnack <= 0) {
                    System.out.println("*** Error, ingresa un precio mayor a 0 ***\n");
                    precioValido = false;
                }
            } catch(NumberFormatException e) {
                System.out.println("*** Error en la entrada de datos, ingresa precio válido ***\n");
                precioValido = false;
            }
        } while(!precioValido);
        servicioSnacks.agregarSnack(new Snack(nombreSnack, precioSnack));
    }

    private static int ingresarOpcion(Scanner sc) {
        int opcion = 0;
        System.out.print("""
                Menu:
                1. Comprar Snack
                2. Mostrar Ticket
                3. Agregar Nuevo Snack
                4. Salir
                Escoge una opcion:\s""");
        try {
            opcion = Integer.parseInt(sc.nextLine().trim());
            if(opcion <= 0 || opcion > 4) {
                System.out.println("*** Error, ingresa una opción válida ***\n");
            }
        } catch(NumberFormatException e) {
            System.out.println("*** Error en la entrada de datos, intenta de nuevo ***\n");
        }
        return opcion;
    }

    private static void ejecutarOpcion(int opcion, Scanner sc, List<Snack>snacksComprados, IServicioSnacks servicioSnacks) {
        switch(opcion) {
            case 1 -> comprarSnack(sc, snacksComprados, servicioSnacks);
            case 2 -> mostrarTicket(snacksComprados);
            case 3 -> crearSnack(sc, servicioSnacks);
            case 4 -> System.out.println("Saliendo del sistema...");
        }
    }

    private static void comprarSnack(Scanner sc, List<Snack>snacksComprados, IServicioSnacks servicioSnacks){
        List<Snack> snacks = servicioSnacks.getSnacks();
        int idSnack = 0;
        boolean entradaValida;
        do {
            entradaValida = true;
            System.out.print("Ingresa el ID del Snack que deseas comprar: ");
            try {
                idSnack = Integer.parseInt(sc.nextLine().trim());
            } catch(NumberFormatException e) {
                System.out.println("*** Error en la entrada de datos, intenta de nuevo ***\n");
                entradaValida = false;
            }
        } while(!entradaValida);

        boolean existeSnack = false;
        for(Snack snack: snacks) {
            if (idSnack == snack.getIdSnack()) {
                snacksComprados.add(snack);
                existeSnack = true;
                System.out.println("""
                        -------------------------------------
                        *** Snack comprado exitosamente! ***
                        -------------------------------------
                        """);
                break;
            }
        }
        if(!existeSnack) {
            System.out.println("*** El snack no existe ***\n");
        }
    }

    private static void mostrarTicket(List<Snack>snacksComprados) {
        double total = 0.0;
        StringBuilder constructorTicket = new StringBuilder("""
                ---------------------------
                *** Ticket de venta ***
                ---------------------------
                """);
        for(Snack snack: snacksComprados) {
            constructorTicket.append(String.format("\t- %s - $%.2f%n", snack.getNombre(), snack.getPrecio()));
            total += snack.getPrecio();
        }
        constructorTicket.append(String.format("\tTotal -> $%.2f%n", total));
        constructorTicket.append("---------------------------\n");
        String ticket = constructorTicket.toString();
        System.out.println(ticket);
    }

}
