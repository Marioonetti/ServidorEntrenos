package model.dto.entrenamiento.add;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.dto.EjercicioDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Serie {
    private int id;
    private int rir;
    private String seriesRepeticiones;
    private int idEjercicio;
    private EjercicioDTO ejercicio;

    public Serie(int id, int rir, String seriesRepeticiones, int idEjercicio) {
        this.id = id;
        this.rir = rir;
        this.seriesRepeticiones = seriesRepeticiones;
        this.idEjercicio = idEjercicio;
    }

    public Serie(int id, int rir, String seriesRepeticiones, EjercicioDTO ejercicio) {
        this.id = id;
        this.rir = rir;
        this.seriesRepeticiones = seriesRepeticiones;
        this.ejercicio = ejercicio;
    }
}
