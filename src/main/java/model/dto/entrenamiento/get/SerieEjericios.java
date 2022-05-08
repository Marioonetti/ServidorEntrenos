package model.dto.entrenamiento.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.dto.EjercicioDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SerieEjericios {
    private int id;
    private int rir;
    private String seriesRepeticiones;
    private EjercicioDTO ejercicioDTO;

}
