package EE.rest.entrenador;


import io.vavr.control.Either;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import model.dto.ApiError;
import model.dto.EntrenadorDTO;
import services.usuarios.EntrenadorService;
import utils.constantes.RestConstants;

import java.util.List;

@Path(RestConstants.ENTRENADOR_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestEntrenador {

    private final EntrenadorService service;

    @Inject
    public RestEntrenador(EntrenadorService service) {
        this.service = service;
    }

    @PUT
    @RolesAllowed({RestConstants.USER_TRAINER})
    public Response addDescripcion(EntrenadorDTO entrenadorDTO){
        Response response = null;
        Either<String, EntrenadorDTO> result = service.addDescripcion(entrenadorDTO);
        if (result.isRight()){

            response = Response
                    .status(Response.Status.CREATED)
                    .entity(result.get())
                    .build();
        }
        else {
            Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(result.getLeft()))
                    .build();
        }

        return response;

    }

    @GET
    @RolesAllowed({RestConstants.USER_CLIENTE})
    public Response getAll(){
        Response response = null;
        Either<String, List<EntrenadorDTO>> result = service.getAllEntrenadores();
        if (result.isRight()){

            response = Response
                    .status(Response.Status.OK)
                    .entity(result.get())
                    .build();
        }
        else {
            Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(result.getLeft()))
                    .build();
        }

        return response;

    }

    @GET
    @Path("/{id}")
    @RolesAllowed({RestConstants.USER_CLIENTE})
    public Response getById(@PathParam("id") int id){
        Response response = null;
        Either<String, EntrenadorDTO> result = service.getById(id);
        if (result.isRight()){

            response = Response
                    .status(Response.Status.OK)
                    .entity(result.get())
                    .build();
        }
        else {
            Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(result.getLeft()))
                    .build();
        }

        return response;

    }

}
