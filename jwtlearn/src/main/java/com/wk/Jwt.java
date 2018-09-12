package com.wk;/**
 * Created by yhopu-pc2 on 2018/7/5.
 */

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wk
 * @className Jwt
 **/
public class Jwt {

    private static final String SECRET = "hdskjhslkdjfskldjfsjkflk89870-9=-08ujhn,dmnv";
    private static final String EXP = "exp";
    private static final String PAYLOAD = "payload";

    /**
     * @param object
     * @param maxAge
     * @param <T>
     * @return the jwt token
     */
    public static <T> String sign(T object, Long maxAge) {

        try {
            final JWTSigner signer = new JWTSigner(SECRET);
            final HashMap<String, Object> claims = new HashMap<String, Object>();
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(object);
            claims.put(PAYLOAD, jsonString);
            claims.put(EXP, System.currentTimeMillis() + maxAge);
            return signer.sign(claims);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
        }
    }

    public static <T> T unsign(String token, Class<T> classT) {
        JWTVerifier jwtVerifier = new JWTVerifier(SECRET);

        try {
            Map<String, Object> claims = jwtVerifier.verify(token);
            if (claims.containsKey(EXP) && claims.containsKey(PAYLOAD)) {
                long exp = (long) claims.get(EXP);
                long currentTimeMillis = System.currentTimeMillis();
                if (exp > currentTimeMillis) {
                    String json = (String) claims.get(PAYLOAD);
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(json, classT);
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
