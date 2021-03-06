package dao;

import dao.datamapper.EntrenadorMapper;
import dao.jdbc.DBConnectionPool;
import dao.utils.Queries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.dto.EntrenadorDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.constantes.Mensajes;

import java.util.List;

@Log4j2
public class EntrenadorDAO {


    private final DBConnectionPool pool;

    @Inject
    public EntrenadorDAO(DBConnectionPool pool) {
        this.pool = pool;
    }


    public Either<String, List<EntrenadorDTO>> getAllEntrenadores() {

        Either<String, List<EntrenadorDTO>> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            List<EntrenadorDTO> entrenadoresList = jdbcTemplate.query(Queries.GET_ALL_ENTRENADORES,
                    new EntrenadorMapper());
            entrenadoresList.removeIf(entrenador ->
                    entrenador.getId() == 0);
            result = Either.right(entrenadoresList);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            result = Either.left(Mensajes.ERROR_DESCONOCIDO);
        }

        return result;


    }

    public Either<String, EntrenadorDTO> updateEntrenador(EntrenadorDTO entrenadorDTO) {

        Either<String, EntrenadorDTO> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            int updated = jdbcTemplate.update(Queries.UPDATE_ENTRENADOR_DATA,
                    entrenadorDTO.getUsername(),
                    entrenadorDTO.getImagen(), entrenadorDTO.getDescripcion(),
                    entrenadorDTO.getId()
            );
            if (updated == 1) {
                result = Either.right(entrenadorDTO);
            } else {
                result = Either.left(Mensajes.ERROR_FALLO_ACTUALIZAR);
            }

        } catch (Exception ex) {
            log.error(ex.getMessage());
            result = Either.left(Mensajes.ERROR_DESCONOCIDO);
        }

        return result;


    }


    public Either<String, EntrenadorDTO> getEntrenadorById(int id) {

        Either<String, EntrenadorDTO> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            EntrenadorDTO entrenador = jdbcTemplate.queryForObject(Queries.ENTRENADOR_BY_ID,
                    BeanPropertyRowMapper.newInstance(EntrenadorDTO.class), id);
            if (entrenador != null) {
                result = Either.right(entrenador);
            } else {
                result = Either.left(Mensajes.NO_EXISE_ESE_USUARIO);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            result = Either.left(Mensajes.ERROR_DESCONOCIDO);
        }

        return result;


    }


}
