package dao.datamapper;

import model.dto.EntrenamientoDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EntrenoMapper implements RowMapper<EntrenamientoDTO> {

    @Override
    public EntrenamientoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EntrenamientoDTO(
                rs.getInt("id"),
                rs.getString("comentario"),
                rs.getInt("idCliente"),
                LocalDate.parse(rs.getDate("fecha").toString()),
                rs.getString("titulo")

        );
    }

}
