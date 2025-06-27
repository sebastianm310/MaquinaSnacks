package maquina_snacks_archivos.servicio;
import maquina_snacks_archivos.dominio.Snack;

import java.util.List;

public interface IServicioSnacks {
    public abstract void agregarSnack(Snack snack);
    public abstract void mostrarSnacks();
    public abstract List<Snack> getSnacks();
}
