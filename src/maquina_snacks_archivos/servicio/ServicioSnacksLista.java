package maquina_snacks_archivos.servicio;

import maquina_snacks_archivos.dominio.Snack;

import java.util.ArrayList;
import java.util.List;

public class ServicioSnacksLista implements IServicioSnacks {
    //Atributo
    private static final List<Snack> snacks;
    //Bloque static inicializador
    static {
        snacks = new ArrayList<>();
        snacks.add(new Snack("Papas", 2000));
        snacks.add(new Snack("Trident", 300));
        snacks.add(new Snack("Avena", 2700));
    }

    public void agregarSnack(Snack snack) {
        snacks.add(snack);
        System.out.println("*** Snack agregado exitosamente ***");
    }

    public void mostrarSnacks() {
        if(snacks.isEmpty()) {
            System.out.println("*** No hay productos disponibles en el inventario ***\n");
        } else {
            System.out.println("***** Snacks disponibles en Inventario *****\n");
            snacks.forEach(System.out::println);
        }
    }

    public List<Snack> getSnacks() {
        return snacks;
    }
}
