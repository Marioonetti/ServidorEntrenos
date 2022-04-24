package model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EjercicioDTO {

    private int id;
    private String nombre;
    private String intensidad;
    private String musculoEnfocado;
    private String img;
    private String descripcion;

}
