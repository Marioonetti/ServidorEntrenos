package model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntrenamientoDTO {

    private int id;
    private String comentario;
    private List<Serie> series;
    private int idCliente;

    public EntrenamientoDTO(int id, String comentario, int idCliente) {
        this.id = id;
        this.comentario = comentario;
        this.idCliente = idCliente;
    }
}
