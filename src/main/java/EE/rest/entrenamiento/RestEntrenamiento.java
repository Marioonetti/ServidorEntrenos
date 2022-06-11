package EE.rest.entrenamiento;


import io.vavr.control.Either;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import model.dto.ApiError;
import model.dto.EntrenamientoDTO;
import services.entrenamiento.EntrenamientoService;
import utils.constantes.RestConstants;
import utils.constantes.RestParams;

import java.util.List;

@Path(RestConstants.ENTRENAMIENTO_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestEntrenamiento {

    private final EntrenamientoService entrenamientoService;

    @Inject
    public RestEntrenamiento(EntrenamientoService entrenamientoService) {
        this.entrenamientoService = entrenamientoService;
    }

    @POST
    @RolesAllowed({RestConstants.USER_TRAINER})
    public Response addEntrenamiento(EntrenamientoDTO entrenamientoDTO) {
        Either<String, EntrenamientoDTO> result = entrenamientoService.addEntreno(entrenamientoDTO);
        Response response = null;
        if (result.isRight()) {

            response = Response
                    .status(Response.Status.OK)
                    .entity(result.get())
                    .build();
        } else {
            Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(result.getLeft()))
                    .build();
        }

        return response;
    }

    @GET
    @Path(RestConstants.ENTRENAMIENTO_DESC_PATH)
    @RolesAllowed({RestConstants.USER_CLIENTE})
    public Response getEntrenosDesc(@QueryParam(RestParams.ID_CLIENTE) int idCliente) {
        Either<String, List<EntrenamientoDTO>> result = entrenamientoService.getEntrenosClienteDesc(idCliente);
        Response response = null;
        if (result.isRight()) {

            response = Response
                    .status(Response.Status.OK)
                    .entity(result.get())
                    .build();
        } else {
            Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(result.getLeft()))
                    .build();
        }

        return response;
    }

    @GET
    @Path(RestConstants.ENTRENAMIENTO_ASC_PATH)
    @RolesAllowed({RestConstants.USER_CLIENTE})
    public Response getEntrenosAsc(@QueryParam(RestParams.ID_CLIENTE) int idCliente) {
        Either<String, List<EntrenamientoDTO>> result = entrenamientoService.getEntrenosClienteAsc(idCliente);
        Response response = null;
        if (result.isRight()) {

            response = Response
                    .status(Response.Status.OK)
                    .entity(result.get())
                    .build();
        } else {
            Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(result.getLeft()))
                    .build();
        }

        return response;
    }

    @GET
    @Path(RestConstants.ENTRENAMIENTO_BY_ID_PATH)
    @RolesAllowed({RestConstants.USER_CLIENTE})
    public Response getEntrenoById(@PathParam(RestParams.ID_PARAM) int id) {
        Either<String, EntrenamientoDTO> result = entrenamientoService.getEntrenosClienteById(id);
        Response response = null;
        if (result.isRight()) {

            response = Response
                    .status(Response.Status.OK)
                    .entity(result.get())
                    .build();
        } else {
            Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(result.getLeft()))
                    .build();
        }

        return response;
    }


}
