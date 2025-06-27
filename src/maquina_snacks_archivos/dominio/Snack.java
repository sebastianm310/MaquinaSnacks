package maquina_snacks_archivos.dominio;

import java.io.Serializable;
import java.util.Objects;

public class Snack implements Serializable{
    //Atributos
    private static int contadorSnacks = 0;
    private int idSnack;
    private String nombre;
    private double precio;

    //Constructores
    public Snack() {
        this.idSnack = ++Snack.contadorSnacks;
    }
    public Snack(String nombre, double precio) {
        this();
        this.nombre = nombre;
        this.precio = precio;
    }

    //Getters y setters
    public int getIdSnack() {
        return this.idSnack;
    }
    public void setIdSnack(int idSnack) {
        this.idSnack = idSnack;
    }
    public String getNombre() {
        return this.nombre;
    }
    public void setNombre() {
        this.nombre = nombre;
    }
    public double getPrecio() {
        return this.precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return String.format("Snack {ID: %d, Nombre: %s, Precio: $%.2f}", this.idSnack, this.nombre, this.precio);
    }

    public String escribirSnack() {
        return String.format("%d,%s,%.1f", this.idSnack, this.nombre, this.precio);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Snack snack = (Snack) o;
        return getIdSnack() == snack.getIdSnack() && Double.compare(getPrecio(), snack.getPrecio()) == 0 && Objects.equals(getNombre(), snack.getNombre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdSnack(), getNombre(), getPrecio());
    }
}
