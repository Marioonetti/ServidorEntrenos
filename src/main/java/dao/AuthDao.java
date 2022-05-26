package dao;

import dao.jdbc.DBConnectionPool;
import dao.utils.Queries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.dto.ClienteDTO;
import model.dto.EntrenadorDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import utils.constantes.Mensajes;

import java.sql.PreparedStatement;
import java.sql.Statement;

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


    public Either<String, ClienteDTO> addUser(ClienteDTO user) {
        Either<String, ClienteDTO> result;
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            jdbcTemplate.update(con -> {
                PreparedStatement preparedStatement = con.prepareStatement(Queries.ADD_CLIENTE,
                        Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setObject(3, user.getNombre());
                preparedStatement.setString(4, user.getApellidos());
                return preparedStatement;
            }, keyHolder);
            user.setId(keyHolder.getKey().intValue());
            result = Either.right(user);

        } catch (EmptyResultDataAccessException sqle) {
            log.error(sqle.getMessage());
            result = Either.left(Mensajes.FALTA_ALGUN_PARAMETRO);

        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            result = Either.left(Mensajes.ESE_USUARIO_YA_EXISTE);
        }

        return result;

    }

    public Either<String, EntrenadorDTO> addEntrenador(EntrenadorDTO entrenador) {
        Either<String, EntrenadorDTO> result;
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            jdbcTemplate.update(con -> {
                PreparedStatement preparedStatement = con.prepareStatement(Queries.ADD_ENTRENADOR,
                        Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, entrenador.getUsername());
                preparedStatement.setString(2, entrenador.getPassword());
                preparedStatement.setString(3, entrenador.getDescripcion());
                preparedStatement.setObject(4, entrenador.getNombre());
                preparedStatement.setString(5, entrenador.getApellidos());
                preparedStatement.setInt(6, entrenador.getEdad());
                preparedStatement.setString(7, entrenador.getImagen());
                return preparedStatement;
            }, keyHolder);
            entrenador.setId(keyHolder.getKey().intValue());
            result = Either.right(entrenador);

        } catch (EmptyResultDataAccessException sqle) {
            log.error(sqle.getMessage());
            result = Either.left(Mensajes.FALTA_ALGUN_PARAMETRO);

        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            result = Either.left(Mensajes.ESE_USUARIO_YA_EXISTE);
        }

        return result;

    }

}
