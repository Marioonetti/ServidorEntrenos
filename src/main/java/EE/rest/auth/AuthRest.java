package EE.rest.auth;


import io.vavr.control.Either;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import model.dto.ApiError;
import model.dto.ClienteDTO;
import model.dto.EntrenadorDTO;
import model.dto.UserDTO;
import services.auth.AuthService;
import utils.constantes.Mensajes;
import utils.constantes.RestConstants;

@Path(RestConstants.AUTHENTICATION_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class AuthRest {

    private final AuthService authService;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public AuthRest(AuthService authService, Pbkdf2PasswordHash passwordHash) {
        this.authService = authService;
        this.passwordHash = passwordHash;
    }

    @POST
    @Path(RestConstants.LOGIN_ENTRENADOR)
    @PermitAll
    public Response loginEntrenador(UserDTO user){
        Response response;
        Either<String, EntrenadorDTO> result = authService.getEntrenador(user.getUsername());
        if (result.isRight()) {
            if (passwordHash.verify(user.getPassw().toCharArray(),
                    result.get().getPassword())) {
                result.get().setPassword(null);
                response = Response.status(Response.Status.OK)
                        .entity(result.get())
                        .build();
            } else {
//                Tratar contra incorrecta
                response = Response.status(Response.Status.NOT_FOUND)
                        .entity(new ApiError(Mensajes.USUARIO_O_CONTRASEÑA_INCORRECTOS))
                        .build();

            }

        } else {
            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(result.getLeft())).build();
        }

        return response;
    }

    @POST
    @Path(RestConstants.LOGIN_CLIENTE)
    @PermitAll
    public Response loginCliente(UserDTO user){
        Response response;
        Either<String, ClienteDTO> result = authService.getCliente(user.getUsername());
        if (result.isRight()) {
            if (passwordHash.verify(user.getPassw().toCharArray(),
                    result.get().getPassword())) {
                result.get().setPassword(null);
                response = Response.status(Response.Status.OK)
                        .entity(result.get())
                        .build();
            } else {
                response = Response.status(Response.Status.NOT_FOUND)
                        .entity(new ApiError(Mensajes.USUARIO_O_CONTRASEÑA_INCORRECTOS))
                        .build();
            }

        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(result.getLeft())).build();
        }

        return response;
    }

}
