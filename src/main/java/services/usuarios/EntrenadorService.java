package services.usuarios;

import dao.EntrenadorDAO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.dto.EntrenadorDTO;
import utils.seguridad.PasswordHash;

import java.util.List;

public class EntrenadorService {
    private final EntrenadorDAO dao;

    private final PasswordHash passwordHash;

    @Inject
    public EntrenadorService(EntrenadorDAO dao, PasswordHash passwordHash) {
        this.dao = dao;
        this.passwordHash = passwordHash;
    }


    public Either<String, List<EntrenadorDTO>> getAllEntrenadores(){
        return dao.getAllEntrenadores();
    }

    public Either<String, EntrenadorDTO> getById(int id){
        return dao.getEntrenadorById(id);
    }

    public Either<String, EntrenadorDTO> updateEntrenador(EntrenadorDTO entrenadorDTO){
        if (!entrenadorDTO.getPassword().startsWith("PBKDF2WithHmacSHA512")){
            entrenadorDTO.setPassword(passwordHash.hash(entrenadorDTO.getPassword()));
        }
        return dao.updateEntrenador(entrenadorDTO);
    }

}
