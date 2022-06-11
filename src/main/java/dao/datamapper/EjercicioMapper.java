package dao.datamapper;

import dao.utils.JDBConstantes;
import model.dto.EjercicioDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EjercicioMapper implements RowMapper<EjercicioDTO> {

    @Override
    public EjercicioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EjercicioDTO(
                rs.getInt(JDBConstantes.ID),
                rs.getString(JDBConstantes.NOMBRE),
                rs.getString(JDBConstantes.INTENSIDAD),
                rs.getString(JDBConstantes.GRUPO_MUSCULAR),
                rs.getString(JDBConstantes.IMG),
                rs.getString(JDBConstantes.DESCRIPCION)
        );
    }

}
