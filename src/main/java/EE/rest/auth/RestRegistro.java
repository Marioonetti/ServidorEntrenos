package EE.rest.auth;


import io.vavr.control.Either;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
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
import services.auth.AuthService;
import utils.constantes.RestConstants;

@Path(RestConstants.REGISTRO_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestRegistro {
    private final AuthService authService;

    @Inject
    public RestRegistro(AuthService authService) {
        this.authService = authService;
    }


    @POST
    @Path(RestConstants.REGISTRO_CLIENTE)
    @PermitAll
    public Response addCliente(ClienteDTO clienteDTO) {
        Response response;
        Either<String, ClienteDTO> result = authService.addCliente(clienteDTO);
        if (result.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(result.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(result.getLeft()))
                    .build();
        }

        return response;
    }


    @POST
    @Path(RestConstants.REGISTRO_ENTRENADOR)
    @RolesAllowed({RestConstants.USER_TRAINER})
    public Response addEntrenador(EntrenadorDTO entrenadorDTO) {
        Response response;
        Either<String, EntrenadorDTO> result = authService.addEntrenador(entrenadorDTO);
        if (result.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(result.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(result.getLeft()))
                    .build();
        }

        return response;
    }


}
