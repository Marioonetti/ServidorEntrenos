package dao.utils;

public class Queries {




    public static final String GET_ALL_EJERCICIOS = "select * from ejercicio";
    public static final String INSERT_EJERCICIO_QUERIE = "insert into ejercicio\n" +
            "    (nombre, intensidad, grupo_muscular, img, descripcion)\n" +
            "    values (?,?,?,?,?);";
    public static final String GET_ENTRENADOR = "select  *  from entrenador where username = ?;";
    public static final String GET_CLIENTE = "select  *  from cliente where username = ?;";
    public static final String ADD_CLIENTE = "insert into cliente (username, password, idEntrenador, nombre, apellidos) VALUES (?, ?, 0, ?, ?)";
    public static final String ADD_ENTRENADOR = "insert into entrenador (username, password, descripcion, nombre, apellidos, edad, imagen) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String INSERT_ENTRENO = "insert into entrenamiento (comentario, idCliente, fecha, titulo) values (?, ?, ?, ?);";
    public static final String INSER_SERIE = "insert into series (rir, series_repeticiones, idEjercicio, enfoque) VALUES (?, ?, ?, ?);";
    public static final String INSERT_ENTRENO_SERIES = "insert into entrenamiento_series (idEntrenamiento, idSerie) VALUES (?, ?);";

    public static final String GET_ENTRENOS_FECHA_ASC = "select  * from entrenamiento where idCliente = ? order by fecha asc ;";
    public static final String GET_ENTRENOS_FECHA_DESC = "select  * from entrenamiento where idCliente = ? order by fecha desc ;";
    public static final String GET_ENTRENO_BY_ID = "select  * from entrenamiento where id = ?;";
    public static final String GET_SERIES_ENTRENO = "select * from series\n" +
            "where id in (select idSerie from entrenamiento_series where idEntrenamiento = ?);";

    public static final String GET_EJERCICIO_BY_ID = "select * from ejercicio where id = ?;";
    public static final String ADD_DESCRIPCION = "update entrenador set descripcion = ? where id = ?;";
    public static final String ALTA_ENTRENADOR = "update cliente set idEntrenador = ? where  id = ?;";
    public static final String BAJA_ENTRENADOR = "update cliente set idEntrenador = 0 where  id = ?;";
    public static final String ENTRENADOR_BY_ID = "select  * from entrenador where id = ?;";
    public static final String GET_EJERCICIOS_POR_NOMBRE = "select * from ejercicio where nombre like concat(?, '%');";
    public static final String GET_ALL_ENTRENADORES = "select *  from entrenador;";
    public static final String SELECT_ID_ENTRENADOR = "select idEntrenador from cliente where id = ?;";
    public static final String GET_CLIENTE_BY_ID = "select * from cliente where id = ?;";
}
