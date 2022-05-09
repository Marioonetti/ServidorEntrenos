package services.usuarios;

import dao.ClienteDAO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.dto.ClienteDTO;

public class ClienteService {

    private final ClienteDAO dao;

    @Inject
    public ClienteService(ClienteDAO dao) {
        this.dao = dao;
    }


    public Either<String, ClienteDTO> darAltaEntrenador(ClienteDTO clienteDTO){
        return dao.darAltaEntrenador(clienteDTO);
    }

    public Either<String, ClienteDTO> darBajaEntrenador(ClienteDTO clienteDTO){
        return dao.darBajaEntrenador(clienteDTO);
    }

}