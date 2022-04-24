package dao;

import dao.jdbc.DBConnectionPool;
import dao.utils.Queries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.dto.ClienteDTO;
import model.dto.EntrenadorDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.constantes.Mensajes;

@Log4j2
public class AuthDao {

    private final DBConnectionPool pool;

    @Inject
    public AuthDao(DBConnectionPool pool) {
        this.pool = pool;
    }

    public Either<String, EntrenadorDTO> getEntrenador(String username) {

        Either<String, EntrenadorDTO> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            EntrenadorDTO entrenador = jdbcTemplate.queryForObject(Queries.GET_ENTRENADOR,
                    BeanPropertyRowMapper.newInstance(EntrenadorDTO.class), username);
            if (entrenador != null) {
                result = Either.right(entrenador);
            } else {
                result = Either.left(Mensajes.NO_EXISE_ESE_USUARIO);
            }

        } catch (Exception ex) {
            log.error(ex.getMessage());
            result = Either.left(Mensajes.ERROR_AL_EXTRAER_LOS_DATOS);

        }

        return result;

    }


    public Either<String, ClienteDTO> getCliente(String username) {

        Either<String, ClienteDTO> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            ClienteDTO cliente = jdbcTemplate.queryForObject(Queries.GET_CLIENTE,
                    BeanPropertyRowMapper.newInstance(ClienteDTO.class), username);
            if (cliente != null) {
                if (cliente.getIdEntrenador() == 1){
                    cliente.setIdEntrenador(0);
                }
                result = Either.right(cliente);
            } else {
                result = Either.left(Mensajes.NO_EXISE_ESE_USUARIO);
            }

        } catch (Exception ex) {
            log.error(ex.getMessage());
            result = Either.left(Mensajes.ERROR_AL_EXTRAER_LOS_DATOS);

        }

        return result;

    }

}
