package dao.datamapper;

import model.dto.EjercicioDTO;
import model.dto.EntrenadorDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EjercicioMapper implements RowMapper<EjercicioDTO> {

    @Override
    public EjercicioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EjercicioDTO(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("intensidad"),
                rs.getString("grupo_muscular"),
                rs.getString("img"),
                rs.getString("descripcion")
        );
    }

}
