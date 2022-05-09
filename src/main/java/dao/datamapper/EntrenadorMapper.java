package dao.datamapper;

import model.dto.EntrenadorDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntrenadorMapper implements RowMapper<EntrenadorDTO> {

    @Override
    public EntrenadorDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EntrenadorDTO(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("descripcion"),
                rs.getString("nombre"),
                rs.getString("apellidos")
        );
    }

}