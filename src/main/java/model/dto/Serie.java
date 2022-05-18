package model.dto;

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
    private String enfoque;

    public Serie(int id, int rir, String seriesRepeticiones, int idEjercicio, String enfoque) {
        this.id = id;
        this.rir = rir;
        this.seriesRepeticiones = seriesRepeticiones;
        this.idEjercicio = idEjercicio;
        this.enfoque = enfoque;
    }

    public Serie(int id, int rir, String seriesRepeticiones, EjercicioDTO ejercicio, String enfoque) {
        this.id = id;
        this.rir = rir;
        this.seriesRepeticiones = seriesRepeticiones;
        this.ejercicio = ejercicio;
        this.enfoque = enfoque;
    }
}
