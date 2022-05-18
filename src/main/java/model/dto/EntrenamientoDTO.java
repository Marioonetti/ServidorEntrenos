package model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    private LocalDate fecha;
    private String titulo;

    public EntrenamientoDTO(int id, String comentario, int idCliente,  LocalDate fecha, String titulo) {
        this.id = id;
        this.comentario = comentario;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.titulo = titulo;
    }
}
