package EE.rest.cliente;

import io.vavr.control.Either;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import model.dto.ApiError;
import model.dto.ClienteDTO;
import services.usuarios.ClienteService;
import utils.constantes.RestConstants;
import utils.constantes.RestParams;

import java.util.List;

@Path(RestConstants.CLIENTE_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestCliente {

    private final ClienteService service;

    @Inject
    public RestCliente(ClienteService service) {
        this.service = service;
    }


    @PUT
    @Path(RestConstants.ALTA_ENTRENADOR)
    @RolesAllowed({RestConstants.USER_CLIENTE})
    public Response altaEntrenador(ClienteDTO clienteDTO) {
        Response response = null;
        Either<String, ClienteDTO> result = service.darAltaEntrenador(clienteDTO);
        if (result.isRight()) {
            response = Response
                    .status(Response.Status.CREATED)
                    .entity(result.get())
                    .build();
        } else {
            response = Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(result.getLeft()))
                    .build();
        }
        return response;
    }

    @PUT
    @Path(RestConstants.BAJA_ENTRENADOR)
    @RolesAllowed({RestConstants.USER_CLIENTE})
    public Response bajaEntrenador(ClienteDTO clienteDTO) {
        Response response = null;
        Either<String, ClienteDTO> result = service.darBajaEntrenador(clienteDTO);
        if (result.isRight()) {

            response = Response
                    .status(Response.Status.CREATED)
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
    @Path(RestConstants.CLIENTE_ID_PATH)
    @RolesAllowed({RestConstants.USER_CLIENTE})
    public Response getClienteById(@PathParam(RestParams.ID_PARAM) int idCliente) {
        Response response = null;
        Either<String, ClienteDTO> result = service.getClienteById(idCliente);
        if (result.isRight()) {

            response = Response
                    .status(Response.Status.CREATED)
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
    @RolesAllowed({RestConstants.USER_TRAINER})
    public Response getClientesPorEntrenador(@QueryParam(RestParams.ID_PARAM) int idEntrenador) {
        Response response = null;
        Either<String, List<ClienteDTO>> result = service.getClientesPorEntrenador(idEntrenador);
        if (result.isRight()) {

            response = Response
                    .status(Response.Status.CREATED)
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
