package model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    private int id;
    private String username;
    private String password;
    private int idEntrenador;
    private String nombre;
    private String apellidos;
}
