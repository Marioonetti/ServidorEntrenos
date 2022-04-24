package utils.seguridad;


import com.google.common.primitives.Bytes;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import utils.constantes.ConstantesEncriptacion;
import utils.constantes.ConstantesNumero;
import utils.constantes.Mensajes;

import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

@Log4j2
public class Cifrado {

    public Either<String, String> cifrarPass(String passw) {
        Either<String, String> result;
        byte[] initialVector = new byte[ConstantesNumero.RANGO_VECTOR_INICIAL];
//        Generamos un vector inicial aleatorio
        byte[] salt = new byte[ConstantesNumero.SALT];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(initialVector);
        sr.nextBytes(salt);
        try {
            Cipher cipher = getCipher(passw, initialVector, salt, Cipher.ENCRYPT_MODE);
            result = Either.right(Base64.getUrlEncoder().encodeToString(Bytes.concat(initialVector, salt,
                    cipher.doFinal(passw.getBytes(StandardCharsets.UTF_8)))));


        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = Either.left(Mensajes.ERROR_CIFRAR);
        }
        return result;

    }


    private Cipher getCipher(String passw, byte[] vectorInicial, byte[] salt, int decriptMode) {
        GCMParameterSpec parameterSpec = new GCMParameterSpec(ConstantesNumero.LONGITUD_SPEC, vectorInicial);
        Cipher cipher = null;

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ConstantesEncriptacion.PBKDF_2_WITH_HMAC_SHA_256);
            KeySpec spec = new PBEKeySpec(passw.toCharArray(), salt, ConstantesNumero.NUMERO_ITERACIONES, ConstantesNumero.LONGITUD_CLAVE);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), ConstantesEncriptacion.ALGORITMO_ENCRIPATACION);

            cipher = Cipher.getInstance(ConstantesEncriptacion.METODO_ENCRIPTACION);
            cipher.init(decriptMode, secretKey, parameterSpec);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return cipher;
    }


}
