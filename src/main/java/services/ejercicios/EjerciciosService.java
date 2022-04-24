package services.ejercicios;

import dao.EjerciciosDAO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.dto.EjercicioDTO;

import java.util.List;

public class EjerciciosService {

    private final EjerciciosDAO dao;

    @Inject
    public EjerciciosService(EjerciciosDAO dao) {
        this.dao = dao;
    }

    public Either<String, List<EjercicioDTO>> getAll(){
        return dao.getAllEjercicios();
    }

    public Either<String, EjercicioDTO> addEjercicio(EjercicioDTO ejercicioDTO){
        return dao.addEjercicio(ejercicioDTO);
    }

}
