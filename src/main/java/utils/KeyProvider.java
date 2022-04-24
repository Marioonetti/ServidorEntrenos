package utils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import utils.constantes.Parametros;

import java.security.Key;

public class KeyProvider {

    @Produces
    @Singleton
    @Named(Parametros.JWT)
    public Key key() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}
