package services.usuarios;

import dao.EntrenadorDAO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.dto.EntrenadorDTO;

import java.util.List;

public class EntrenadorService {
    private final EntrenadorDAO dao;

    @Inject
    public EntrenadorService(EntrenadorDAO dao) {
        this.dao = dao;
    }

    public Either<String, EntrenadorDTO> addDescripcion(EntrenadorDTO entrenadorDTO){
        return dao.addDescipcion(entrenadorDTO);
    }

    public Either<String, List<EntrenadorDTO>> getAllEntrenadores(){
        return dao.getAllEntrenadores();
    }

    public Either<String, EntrenadorDTO> getById(int id){
        return dao.getEntrenadorById(id);
    }

}
