package dao.utils;

public class Queries {




    public static final String GET_ALL_EJERCICIOS = "select * from ejercicio";
    public static final String INSERT_EQUIPO_QUERIE = "insert into ejercicio\n" +
            "    (nombre, intensidad, musculoEnfocado, img, descripcion)\n" +
            "    values (?,?,?,?,?);";
    public static final String GET_ENTRENADOR = "select  *  from entrenador where username = ?;";
    public static final String GET_CLIENTE = "select  *  from cliente where username = ?;";
}
