package services.entrenamiento;

import dao.EntrenamientoDAO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.dto.EntrenamientoDTO;

import java.util.List;

public class EntrenamientoService {

    private final EntrenamientoDAO dao;

    @Inject
    public EntrenamientoService(EntrenamientoDAO dao) {
        this.dao = dao;
    }

    public Either<String, EntrenamientoDTO> addEntreno(EntrenamientoDTO entreno) {
        return dao.addEntreno(entreno);
    }

    public Either<String, List<EntrenamientoDTO>> getEntrenosCliente(int idCliente) {
        return dao.getEntrenos(idCliente);
    }
}
