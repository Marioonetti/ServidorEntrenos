package dao;

import dao.datamapper.EjercicioMapper;
import dao.jdbc.DBConnectionPool;
import dao.utils.Queries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.dto.EjercicioDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import utils.constantes.Mensajes;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Log4j2
public class EjerciciosDAO {


    private final DBConnectionPool pool;

    @Inject
    public EjerciciosDAO(DBConnectionPool pool) {
        this.pool = pool;
    }

    public Either<String, List<EjercicioDTO>> getAllEjercicios(){

        Either<String, List<EjercicioDTO>> result;

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            List<EjercicioDTO> purchaseList = jdbcTemplate.query(Queries.GET_ALL_EJERCICIOS,
                    new BeanPropertyRowMapper<>(EjercicioDTO.class));
            result = Either.right(purchaseList);

        }catch (DataAccessException ex){
            log.error(ex.getMessage());
            result = Either.left(Mensajes.ERROR_AL_EXTRAER_LOS_DATOS);

        }
        return result;
    }


    public Either<String, List<EjercicioDTO>> getEjerciciosByName(String nombre){
        Either<String, List<EjercicioDTO>> result;

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            List<EjercicioDTO> purchaseList = jdbcTemplate.query(Queries.GET_EJERCICIOS_POR_NOMBRE,
                    new EjercicioMapper(), nombre);
            result = Either.right(purchaseList);

        }catch (DataAccessException ex){
            log.error(ex.getMessage());
            result = Either.left(Mensajes.ERROR_DESCONOCIDO);

        }
        return result;
    }

    public Either<String, EjercicioDTO> getEjercicioById(int id){

        Either<String, EjercicioDTO> result;

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            EjercicioDTO ejercicio = jdbcTemplate.queryForObject(Queries.GET_EJERCICIO_BY_ID,
                    new BeanPropertyRowMapper<>(EjercicioDTO.class), id);
            result = Either.right(ejercicio);

        }catch (DataAccessException ex){
            log.error(ex.getMessage());
            result = Either.left(Mensajes.ERROR_AL_EXTRAER_LOS_DATOS);

        }
        return result;
    }


    public Either<String, EjercicioDTO> addEjercicio(EjercicioDTO ejercicioDTO) {
        Either<String, EjercicioDTO> result;

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement =
                        connection.prepareStatement(Queries.INSERT_EQUIPO_QUERIE, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, ejercicioDTO.getNombre());
                preparedStatement.setString(2, ejercicioDTO.getIntensidad());
                preparedStatement.setString(3, ejercicioDTO.getGrupoMuscular());
                preparedStatement.setString(4, ejercicioDTO.getImg());
                preparedStatement.setString(5, ejercicioDTO.getDescripcion());
                return preparedStatement;
            }, keyHolder);

            ejercicioDTO.setId(keyHolder.getKey().intValue());
            result = Either.right(ejercicioDTO);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            result = Either.left(Mensajes.EJERCICIO_EXISTENTE);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            result = Either.left(Mensajes.ERROR_AL_EXTRAER_LOS_DATOS);

        }


        return result;
    }


}
