package model.dto.entrenamiento.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntrenamientoClienteDTO {

    private int id;
    private String comentario;
    private ArrayList<SerieEjericios> series;
    private int idCliente;

}
