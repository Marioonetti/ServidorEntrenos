package model.dto;

import jakarta.ws.rs.GET;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EntrenadorDTO {
    private int id;
    private String username;
    private String password;
    private String descripcion;
    private String nombre;
    private String apellidos;

    public EntrenadorDTO(int id, String username, String descripcion, String nombre, String apellidos) {
        this.id = id;
        this.username = username;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }
}
