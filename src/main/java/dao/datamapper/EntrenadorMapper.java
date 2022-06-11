package dao.datamapper;

import dao.utils.JDBConstantes;
import model.dto.EntrenadorDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntrenadorMapper implements RowMapper<EntrenadorDTO> {

    @Override
    public EntrenadorDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EntrenadorDTO(
                rs.getInt(JDBConstantes.ID),
                rs.getString(JDBConstantes.DESCRIPCION),
                rs.getString(JDBConstantes.NOMBRE),
                rs.getString(JDBConstantes.APELLIDOS),
                rs.getInt(JDBConstantes.EDAD),
                rs.getString(JDBConstantes.IMAGEN)
        );
    }

}