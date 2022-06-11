package EE.security;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import model.dto.ClienteDTO;
import model.dto.EntrenadorDTO;
import services.auth.AuthService;
import utils.constantes.RestConstants;

import java.util.Collections;
import java.util.Set;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

public class InMemoryIdentityStore implements IdentityStore {

    private final AuthService authService;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public InMemoryIdentityStore(AuthService authService, Pbkdf2PasswordHash passwordHash) {
        this.authService = authService;
        this.passwordHash = passwordHash;
    }

    @Override
    public int priority() {
        return 10;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {

        CredentialValidationResult credentialValidationResult = null;

        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential user = (UsernamePasswordCredential) credential;


            Either<String, ClienteDTO> resultObetenerCli = authService.getCliente(user.getCaller());

            if (resultObetenerCli.isRight()) {
                if (passwordHash.verify(user.getPasswordAsString().toCharArray(), resultObetenerCli.get().getPassword())) {
                    credentialValidationResult = new CredentialValidationResult(user.getCaller(), Collections.singleton(RestConstants.USER_CLIENTE));
                } else {

                    credentialValidationResult = INVALID_RESULT;
                }
            } else {

                Either<String, EntrenadorDTO> resultObtenerEntrenador = authService.getEntrenador(user.getCaller());
                if (resultObtenerEntrenador.isRight()) {
                    if (passwordHash.verify(user.getPasswordAsString().toCharArray(), resultObtenerEntrenador.get().getPassword())) {
                        credentialValidationResult = new CredentialValidationResult(user.getCaller(), Set.of(RestConstants.USER_CLIENTE, RestConstants.USER_TRAINER));
                    } else {

                        credentialValidationResult = INVALID_RESULT;
                    }
                } else {

                    credentialValidationResult = INVALID_RESULT;
                }
            }


        }
        return credentialValidationResult;
    }


}
