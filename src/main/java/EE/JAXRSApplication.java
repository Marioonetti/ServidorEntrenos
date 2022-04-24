package EE;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import utils.constantes.RestConstants;

@ApplicationPath(RestConstants.PATH_API)
@DeclareRoles({RestConstants.USER_CLIENTE, RestConstants.USER_TRAINER})
public class JAXRSApplication extends Application {
    }
