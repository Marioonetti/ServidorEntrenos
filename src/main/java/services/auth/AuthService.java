package services.auth;

import dao.AuthDao;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import model.dto.ClienteDTO;
import model.dto.EntrenadorDTO;
import utils.constantes.Mensajes;
import utils.seguridad.PasswordHash;

public class AuthService {
    private final AuthDao dao;
    private final PasswordHash passwordHash;
    private final Pbkdf2PasswordHash pbkdf2PasswordHash;

    @Inject
    public AuthService(AuthDao dao, PasswordHash passwordHash, Pbkdf2PasswordHash pbkdf2PasswordHash) {
        this.dao = dao;
        this.passwordHash = passwordHash;
        this.pbkdf2PasswordHash = pbkdf2PasswordHash;
    }

    public Either<String, EntrenadorDTO> getEntrenador(String username) {
        return dao.getEntrenador(username);
    }

    public Either<String, ClienteDTO> getCliente(String username) {
        return dao.getCliente(username);
    }

    public Either<String, EntrenadorDTO> getEntrenador(String username, char[] pass) {
        Either<String, EntrenadorDTO> resultDao = dao.getEntrenador(username);
        Either<String, EntrenadorDTO> result;

        if (resultDao.isRight()) {
            if (pbkdf2PasswordHash.verify(pass,
                    resultDao.get().getPassword())) {
                resultDao.get().setPassword(null);
                result = Either.right(resultDao.get());
            } else {
                result = Either.left(Mensajes.NO_EXISE_ESE_USUARIO);

            }
        } else {
            result = Either.left(resultDao.getLeft());
        }
        return result;
    }

    public Either<String, ClienteDTO> getCliente(String username, char[] pass) {
        Either<String, ClienteDTO> resultDao = dao.getCliente(username);
        Either<String, ClienteDTO> result;

        if (resultDao.isRight()) {
            if (pbkdf2PasswordHash.verify(pass,
                    resultDao.get().getPassword())) {
                resultDao.get().setPassword(null);
                result = Either.right(resultDao.get());
            } else {

                result = Either.left(Mensajes.NO_EXISE_ESE_USUARIO);

            }
        } else {
            result = Either.left(resultDao.getLeft());
        }
        return result;
    }

    public Either<String, EntrenadorDTO> addEntrenador(EntrenadorDTO entrenadorDTO) {
        entrenadorDTO.setPassword(passwordHash.hash(entrenadorDTO.getPassword()));
        return dao.addEntrenador(entrenadorDTO);
    }

    public Either<String, ClienteDTO> addCliente(ClienteDTO clienteDTO) {
        clienteDTO.setPassword(passwordHash.hash(clienteDTO.getPassword()));
        return dao.addUser(clienteDTO);
    }
}
