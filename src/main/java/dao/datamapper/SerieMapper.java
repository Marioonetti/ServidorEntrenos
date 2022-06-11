package dao.datamapper;

import dao.utils.JDBConstantes;
import model.dto.Serie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SerieMapper implements RowMapper<Serie> {

    @Override
    public Serie mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Serie(
                rs.getInt(JDBConstantes.ID),
                rs.getInt(JDBConstantes.RIR),
                rs.getString(JDBConstantes.SERIES_REPETICIONES),
                rs.getInt(JDBConstantes.ID_EJERCICIO),
                rs.getString(JDBConstantes.ENFOQUE)
        );
    }

}