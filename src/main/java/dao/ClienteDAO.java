package dao;

import dao.jdbc.DBConnectionPool;
import dao.utils.Queries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.dto.ClienteDTO;
import model.dto.EntrenadorDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.constantes.Mensajes;

import java.time.LocalDateTime;

@Log4j2
public class ClienteDAO {

    private final DBConnectionPool pool;

    @Inject
    public ClienteDAO(DBConnectionPool pool) {
        this.pool = pool;
    }


    public Either<String, ClienteDTO> darAltaEntrenador(ClienteDTO cliente){
        Either<String, ClienteDTO> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());

            int idEntrenador = jdbcTemplate.queryForObject(Queries.SELECT_ID_ENTRENADOR,
                    Integer.class, cliente.getId());

            if (idEntrenador == 0){
                jdbcTemplate.update(Queries.ALTA_ENTRENADOR,
                        cliente.getIdEntrenador(),
                        cliente.getId());
                result = Either.right(cliente);
            }
            else {
                result = Either.left(Mensajes.ERROR_ALTA_CLIENTE);
            }

        } catch (Exception ex) {
            log.error(ex.getMessage());
            result = Either.left(Mensajes.ERROR_DESCONOCIDO);
        }

        return result;


    }


    public Either<String, ClienteDTO> darBajaEntrenador(ClienteDTO clienteDTO){
        Either<String, ClienteDTO> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            int idEntrenador = jdbcTemplate.queryForObject(Queries.SELECT_ID_ENTRENADOR,
                    Integer.class, clienteDTO.getId());

            if (idEntrenador != 0){
                jdbcTemplate.update(Queries.BAJA_ENTRENADOR,
                        clienteDTO.getId());
                clienteDTO.setIdEntrenador(0);
                result = Either.right(clienteDTO);
            }
            else {
                result = Either.left(Mensajes.ERROR_BAJA_ENTRENADOR);
            }

        } catch (Exception ex) {
            log.error(ex.getMessage());
            result = Either.left(Mensajes.ERROR_DESCONOCIDO);
        }

        return result;


    }


}
