package dao;

import dao.datamapper.EntrenoMapper;
import dao.datamapper.SerieMapper;
import dao.jdbc.DBConnectionPool;
import dao.utils.Queries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.dto.EjercicioDTO;
import model.dto.EntrenamientoDTO;
import model.dto.Serie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import utils.constantes.Mensajes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

@Log4j2
public class EntrenamientoDAO {


    private final DBConnectionPool pool;
    private final EjerciciosDAO ejerciciosDAO;

    @Inject
    public EntrenamientoDAO(DBConnectionPool pool, EjerciciosDAO ejerciciosDAO) {
        this.pool = pool;
        this.ejerciciosDAO = ejerciciosDAO;
    }


    public Either<String, EntrenamientoDTO> addEntreno(EntrenamientoDTO entreno) {
        Either<String, EntrenamientoDTO> result;

        try {
            //Primero Añado el entreno para que me de la id
            KeyHolder keyHolder = new GeneratedKeyHolder();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement =
                        connection.prepareStatement(Queries.INSERT_ENTRENO, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, entreno.getComentario());
                preparedStatement.setInt(2, entreno.getIdCliente());
                preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
                preparedStatement.setString(4, entreno.getTitulo());
                return preparedStatement;
            }, keyHolder);
            int idEntreno = keyHolder.getKey().intValue();
            entreno.setId(idEntreno);

//            Añado las series para que a cada una le genere su id
            entreno.getSeries().forEach(serie ->
                    {
                        jdbcTemplate.update(connection -> {
                            PreparedStatement preparedStatement =
                                    connection.prepareStatement(Queries.INSER_SERIE, Statement.RETURN_GENERATED_KEYS);
                            preparedStatement.setInt(1, serie.getRir());
                            preparedStatement.setString(2, serie.getSeriesRepeticiones());
                            preparedStatement.setInt(3, serie.getIdEjercicio());
                            preparedStatement.setString(4, serie.getEnfoque());
                            return preparedStatement;
                        }, keyHolder);
                        int idSerie = keyHolder.getKey().intValue();
                        serie.setId(idSerie);
                    }
            );

//            Añado la id del entreno con la de las series

            entreno.getSeries().forEach(serie ->
                    jdbcTemplate.update(connection -> {
                        PreparedStatement preparedStatement =
                                connection.prepareStatement(Queries.INSERT_ENTRENO_SERIES);
                        preparedStatement.setInt(1, idEntreno);
                        preparedStatement.setInt(2, serie.getId());
                        return preparedStatement;
                    })
            );

            result = Either.right(entreno);

        } catch (Exception ex) {
            log.error(ex.getMessage());
            result = Either.left(Mensajes.ERROR_AL_ANIADIR);

        }


        return result;
    }


    public Either<String, EntrenamientoDTO> getEntrenosById(int idEntreno) {
        Either<String, EntrenamientoDTO> result;
        EntrenamientoDTO entreno = null;

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
//            Primero Saco todos los entrenos por id

            entreno = jdbcTemplate.queryForObject(Queries.GET_ENTRENO_BY_ID,
                    new EntrenoMapper(), idEntreno);

            List<Serie> listSerie = getSeries(jdbcTemplate, entreno);
            entreno.setSeries(listSerie);
//                Saco los ejs y se los añado a cada serie
            listSerie.forEach(serie -> {
                Either<String, EjercicioDTO> ejById = ejerciciosDAO.getEjercicioById(serie.getIdEjercicio());
                if (ejById.isRight()) {
                    serie.setEjercicio(ejById.get());
                }
            });

            result = Either.right(entreno);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            result = Either.left(Mensajes.ERROR_AL_EXTRAER_LOS_DATOS);

        }


        return result;
    }

    public Either<String, List<EntrenamientoDTO>> getEntrenosDesc(int idCliente) {
        Either<String, List<EntrenamientoDTO>> result;
        List<EntrenamientoDTO> listEntrenos;

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());

            listEntrenos = jdbcTemplate.query(Queries.GET_ENTRENOS_FECHA_DESC,
                    new EntrenoMapper(), idCliente);
            completeData(jdbcTemplate, listEntrenos);

            result = Either.right(listEntrenos);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            result = Either.left(Mensajes.ERROR_AL_EXTRAER_LOS_DATOS);

        }


        return result;
    }

    public Either<String, List<EntrenamientoDTO>> getEntrenosFechaAsc(int idCliente) {
        Either<String, List<EntrenamientoDTO>> result;
        List<EntrenamientoDTO> listEntrenos;

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
//            Primero Saco todos los entrenos por id

            listEntrenos = jdbcTemplate.query(Queries.GET_ENTRENOS_FECHA_ASC,
                    new EntrenoMapper(), idCliente);

            completeData(jdbcTemplate, listEntrenos);

            result = Either.right(listEntrenos);

        } catch (Exception ex) {
            log.error(ex.getMessage());
            result = Either.left(Mensajes.ERROR_AL_EXTRAER_LOS_DATOS);

        }


        return result;
    }


    private void completeData(JdbcTemplate jdbcTemplate, List<EntrenamientoDTO> listEntrenos) {
        listEntrenos.forEach(entreno -> {
//                Saco todas las series de ese entreno

            List<Serie> listSerie = getSeries(jdbcTemplate, entreno);
            entreno.setSeries(listSerie);

//                Saco los ejs y se los añado a cada serie
            listSerie.forEach(serie -> {
                Either<String, EjercicioDTO> ejById = ejerciciosDAO.getEjercicioById(serie.getIdEjercicio());
                if (ejById.isRight()) {
                    serie.setEjercicio(ejById.get());
                }
            });

        });
    }


    private List<Serie> getSeries(JdbcTemplate jdbcTemplate, EntrenamientoDTO entreno) {
        return jdbcTemplate.query(Queries.GET_SERIES_ENTRENO,
                new SerieMapper(), entreno.getId());
    }

}
