package EE.rest.auth;


import io.vavr.control.Either;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import model.dto.ApiError;
import model.dto.ClienteDTO;
import model.dto.EntrenadorDTO;
import model.dto.UserDTO;
import services.auth.AuthService;
import utils.constantes.RestConstants;

@Path(RestConstants.LOGIN_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestLogin {

    private final AuthService authService;

    @Inject
    public RestLogin(AuthService authService) {
        this.authService = authService;

    }

    @POST
    @Path(RestConstants.LOGIN_ENTRENADOR)
    @PermitAll
    public Response loginEntrenador(UserDTO user) {
        Response response;
        Either<String, EntrenadorDTO> result = authService.getEntrenador(user.getUsername(), user.getPassw().toCharArray());
        if (result.isRight()) {

            response = Response.status(Response.Status.OK)
                    .entity(result.get())
                    .build();

        } else {
            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(result.getLeft())).build();
        }

        return response;
    }

    @POST
    @Path(RestConstants.LOGIN_CLIENTE)
    @PermitAll
    public Response loginCliente(UserDTO user) {
        Response response;
        Either<String, ClienteDTO> result = authService.getCliente(user.getUsername(), user.getPassw().toCharArray());
        if (result.isRight()) {

            response = Response.status(Response.Status.OK)
                    .entity(result.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(result.getLeft())).build();
        }

        return response;
    }

}
