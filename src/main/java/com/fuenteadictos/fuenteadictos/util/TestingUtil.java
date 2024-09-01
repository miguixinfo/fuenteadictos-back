package com.fuenteadictos.fuenteadictos.util;

import java.security.Key;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

public class TestingUtil {
    public static void main(String[] args) {
        secretKeyGenerator();
    }

    public static void secretKeyGenerator() {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // genera una clave segura de al menos 256 bits
        String base64Key = Encoders.BASE64.encode(key.getEncoded()); // codificar en Base64 para uso en propiedades

        System.out.println("Clave secreta segura para JWT (Base64): " + base64Key);
    }
}
