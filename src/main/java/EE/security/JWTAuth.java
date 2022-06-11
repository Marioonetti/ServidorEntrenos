package EE.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.log4j.Log4j2;
import utils.constantes.Mensajes;
import utils.constantes.Parametros;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Singleton
@Log4j2
public class JWTAuth implements HttpAuthenticationMechanism {


    private final InMemoryIdentityStore inMemoryIdentityStore;
    private final Key key;

    @Inject
    public JWTAuth(InMemoryIdentityStore inMemoryIdentityStore, @Named(Parametros.JWT) Key key) {
        this.inMemoryIdentityStore = inMemoryIdentityStore;
        this.key = key;
    }


    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                HttpMessageContext httpMessageContext) throws AuthenticationException {

        CredentialValidationResult credentialValidationResult = CredentialValidationResult.INVALID_RESULT;

        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            String[] valores = header.split(Parametros.COMILLAS_SERPARADAS);

            if (valores[0].equalsIgnoreCase(Parametros.BASIC_HEADER)) {
                String userPass = new String(Base64.getUrlDecoder().decode(valores[1]));
                String[] userPassSeparado = userPass.split(Parametros.DOS_PUNTOS);

                credentialValidationResult = inMemoryIdentityStore
                        .validate(new UsernamePasswordCredential(userPassSeparado[0], userPassSeparado[1]));

                if (credentialValidationResult.getStatus() == CredentialValidationResult.Status.VALID) {

//                     Crear token

                    String token = generateToken(credentialValidationResult);
                    httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, token);
                }
            } else if (valores[0].equalsIgnoreCase(Parametros.BEARER_HEADER)) {

                try {
                    Jws<Claims> jws = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(valores[1]);

                    List<String> grupos = (List<String>) jws.getBody().get(Parametros.GROUP);


                    credentialValidationResult = new CredentialValidationResult(
                            jws.getBody().get(Parametros.USER).toString(),
                            new HashSet<>(grupos));

                } catch (ExpiredJwtException expired) {
                    log.error(expired.getMessage(), expired);
                    httpServletResponse.setHeader(HttpHeaders.EXPIRES, Mensajes.EL_TIEMPO_DEL_TOKEN_HA_EXPIRADO);
                } catch (MalformedJwtException modificado) {
                    log.error(modificado.getMessage(), modificado);
                    httpServletResponse.setHeader(Parametros.ERROR_HEADER, Mensajes.TOKEN_ERROR);
                } catch (SignatureException exception) {
                    log.error(exception.getMessage(), exception);
                    httpServletResponse.setHeader(Parametros.MODIFICADO_HEADER, Mensajes.EL_TIEMPO_DEL_TOKEN_HA_SIDO_MODIFIFCADO);

                }
            }

        }
        if (credentialValidationResult.getStatus().equals(CredentialValidationResult.Status.INVALID)) {
            return httpMessageContext.doNothing();
        }
        return httpMessageContext.notifyContainerAboutLogin(credentialValidationResult);
    }


    private String generateToken(CredentialValidationResult credentialValidationResult) {

        return Jwts.builder()
                .setExpiration(Date.from(
                        LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault())
                                .toInstant())
                ).claim(Parametros.USER, credentialValidationResult.getCallerPrincipal().getName())
                .claim(Parametros.GROUP, credentialValidationResult.getCallerGroups())
                .signWith(key).compact();
    }
}
