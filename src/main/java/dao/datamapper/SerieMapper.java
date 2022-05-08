package dao.datamapper;

import model.dto.entrenamiento.add.EntrenamientoDTO;
import model.dto.entrenamiento.add.Serie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SerieMapper implements RowMapper<Serie> {

    @Override
    public Serie mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Serie(
                rs.getInt("id"),
                rs.getInt("rir"),
                rs.getString("series_repeticiones"),
                rs.getInt("idEjercicio")
        );
    }

}