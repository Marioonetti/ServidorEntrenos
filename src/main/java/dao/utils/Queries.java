package dao.utils;

public class Queries {




    public static final String GET_ALL_EJERCICIOS = "select * from ejercicio";
    public static final String INSERT_EQUIPO_QUERIE = "insert into ejercicio\n" +
            "    (nombre, intensidad, musculoEnfocado, img, descripcion)\n" +
            "    values (?,?,?,?,?);";
    public static final String GET_ENTRENADOR = "select  *  from entrenador where username = ?;";
    public static final String GET_CLIENTE = "select  *  from cliente where username = ?;";
    public static final String ADD_CLIENTE = "insert into cliente (username, password, idEntrenador, nombre, apellidos) VALUES (?, ?, ?, ?, ?)";
    public static final String ADD_ENTRENADOR = "insert into entrenador (username, password, descripcion, nombre, apellidos) VALUES (?, ?, ?, ?, ?)";
    public static final String INSERT_ENTRENO = "insert into entrenamiento (comentario, idCliente) values (?, ?);";
    public static final String INSER_SERIE = "insert into series (rir, series_repeticiones, idEjercicio) VALUES (?, ?, ?);";
    public static final String INSERT_ENTRENO_SERIES = "insert into entrenamiento_series (idEntrenamiento, idSerie) VALUES (?, ?);";
    public static final String ENTRENAMIENTOS_POR_CLIENTE = "\n" +
            "select * from (\n" +
            "               (series join entrenamiento_series es on es.idSerie = id)\n" +
            "                  join ejercicio on ejercicio.id = series.idEjercicio)\n" +
            "where idSerie in (select idSerie\n" +
            "                  from entrenamiento_series where idEntrenamiento in\n" +
            "                                                  (select id from entrenamiento where idCliente = ?))\n" +
            ";\n" +
            "\n";
    public static final String GET_ENTRENOS_CLIENTE = "select *  from entrenamiento where idCliente = ?";
    public static final String GET_SERIES_ENTRENO = "select * from series\n" +
            "where id in (select idSerie from entrenamiento_series where idEntrenamiento = ?);";

    public static final String GET_EJERCICIO_BY_ID = "select * from ejercicio where id = ?;";
}
