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
    private int edad;
    private String imagen;

    public EntrenadorDTO(int id, String descripcion, String nombre, String apellidos, int edad, String imagen) {
        this.id = id;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.imagen = imagen;
    }
}
