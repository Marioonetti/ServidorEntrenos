package services.auth;

import dao.AuthDao;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.dto.ClienteDTO;
import model.dto.EntrenadorDTO;

public class AuthService {
    private final AuthDao dao;

    @Inject
    public AuthService(AuthDao dao) {
        this.dao = dao;
    }

    public Either<String, EntrenadorDTO> getEntrenador(String username) {
        return dao.getEntrenador(username);
    }

    public Either<String, ClienteDTO> getCliente(String username) {
        return dao.getCliente(username);
    }
}
