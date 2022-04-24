package EE.rest.ejercicios;


import io.vavr.control.Either;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import model.dto.ApiError;
import model.dto.EjercicioDTO;
import services.ejercicios.EjerciciosService;
import utils.constantes.RestConstants;

import java.util.List;

@Path(RestConstants.EJERCICIOS_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestEjercicios {


    private final EjerciciosService ejerciciosService;


    @Inject
    public RestEjercicios(EjerciciosService ejerciciosService) {
        this.ejerciciosService = ejerciciosService;
    }


    @GET
    @RolesAllowed({RestConstants.USER_CLIENTE})
    public Response getAllEjercicios(){
        Either<String, List<EjercicioDTO>> result = ejerciciosService.getAll();
        Response response = null;

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

    @POST
    @RolesAllowed({RestConstants.USER_TRAINER})
    public Response addEjercicio(EjercicioDTO ejercicioDTO){
        Either<String, EjercicioDTO> result = ejerciciosService.addEjercicio(ejercicioDTO);
        Response response = null;

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
