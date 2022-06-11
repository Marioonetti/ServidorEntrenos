package dao.datamapper;

import dao.utils.JDBConstantes;
import model.dto.EntrenamientoDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EntrenoMapper implements RowMapper<EntrenamientoDTO> {

    @Override
    public EntrenamientoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EntrenamientoDTO(
                rs.getInt(JDBConstantes.ID),
                rs.getString(JDBConstantes.COMENTARIO),
                rs.getInt(JDBConstantes.ID_CLIENTE),
                LocalDate.parse(rs.getDate(JDBConstantes.FECHA).toString()),
                rs.getString(JDBConstantes.TITULO)

        );
    }

}
