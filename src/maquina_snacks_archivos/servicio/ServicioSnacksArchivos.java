package maquina_snacks_archivos.servicio;

import maquina_snacks_archivos.dominio.Snack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ServicioSnacksArchivos implements IServicioSnacks{
    //Atributo (nombre del archivo)
    private final String NOMBRE_ARCHIVO = "snacks.txt";
    //Crear la lista de snacks
    private List<Snack> snacks = new ArrayList<>();

    //Constructor
    public ServicioSnacksArchivos() {
        //Creamos una instancia de File
        File archivo = new File(NOMBRE_ARCHIVO);
        boolean existe = false;
        //Intentamos crear el archivo si no existe, de lo contrario mostrara el contenido
        //del archivo ya existente
        try {
            existe = archivo.exists();
            if(existe) {
                this.snacks = obtenerSnacks();
            } else {
                PrintWriter salida = new PrintWriter(new FileWriter(archivo));
                salida.close(); //Guardar archivo
                System.out.println("*** El archivo ha sido creado ***");
            }
        } catch(IOException e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
        //Si no existe, cargamos algunos snacks iniciales
        if(!existe) {
            cargarSnacksIniciales();
        }
    }

    //Escribir snacks iniciales del programa
    private void cargarSnacksIniciales() {
        this.agregarSnack(new Snack("Chocolate", 2000));
        this.agregarSnack(new Snack("Trident", 1000));
        this.agregarSnack(new Snack("Papas", 2300));
    }

    private List<Snack> obtenerSnacks() {
        List <Snack>snacks = new ArrayList<>();
        try {
            List<String> lineas = Files.readAllLines(Paths.get(NOMBRE_ARCHIVO));
            for(String linea: lineas) {
                String[] lineaSnack = linea.split(",");
                String idSnack = lineaSnack[0];
                String nombreSnack = lineaSnack[1];
                double precioSnack = Double.parseDouble(lineaSnack[2]);
                Snack snack = new Snack(nombreSnack, precioSnack);
                snacks.add(snack); //Agrega el snack a la List
            }
        } catch(IOException e) {
            System.out.println("Error al leer archivo de snacks: " + e.getMessage());
        }
        return snacks;
    }

    //Agregar snack a la lista e invocar hacia el archivo
    @Override
    public void agregarSnack(Snack snack) {
        //Agregamos el snack a la lista
        this.snacks.add(snack);
        //Guardar informacion del nuevo snack en el archivo
        this.agregarSnackArchivo(snack);
    }

    //Escribir los snacks en el archivo
    private void agregarSnackArchivo(Snack snack) {
        boolean anexar = false;
        File archivo = new File(NOMBRE_ARCHIVO);
        try {
            anexar = archivo.exists();
            PrintWriter salida = new PrintWriter(new FileWriter(archivo, anexar));
            salida.println(snack.escribirSnack());
            salida.close(); //Cerrar para guardar siempre
        } catch(IOException e) {
            System.out.println("Error al agregar al archivo: " + e.getMessage());
        }
    }

    @Override
    public void mostrarSnacks() {
        if(snacks.isEmpty()) {
            System.out.println("*** No hay productos disponibles en el inventario ***\n");
        } else {
            System.out.println("***** Snacks disponibles en Inventario *****\n");
            snacks.forEach(System.out::println);
        }
    }

    @Override
    public List<Snack> getSnacks() {
        return this.snacks;
    }
}
