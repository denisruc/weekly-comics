package net.sqlengineer.comics.client;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by druckebusch on 5/30/17.
 */

public class AuthenticationInterceptor implements Interceptor {

    private static final String TIMESTAMP = "ts";
    private static final String HASH = "hash";
    private static final String API_KEY = "apikey";

    // TODO restore correct public key
    private static final String PUBLIC_KEY = "6bfa808583b9f738c62a452a8f1aa085";
    //TODO restore correct private key and obfuscate 
    private static final String PRIVATE_KEY = "6dfe58b166f9a71795fd1047233f343eb222a284";


    @Override
    public Response intercept(Chain chain) throws IOException {
        String timestamp = Long.toString(System.currentTimeMillis());
        // TODO compute hash
        String hashInput = timestamp + PRIVATE_KEY + PUBLIC_KEY;

        MessageDigest md5Encoder = null;

        try {
            md5Encoder = MessageDigest.getInstance("MD5");
        } catch(NoSuchAlgorithmException e) {
            throw new IOException(e.getMessage(), e);

        }
        byte[] md5Bytes = md5Encoder.digest(hashInput.getBytes());
        StringBuilder md5 = new StringBuilder();
        for (int i = 0; i < md5Bytes.length; ++i) {
            md5.append(Integer.toHexString((md5Bytes[i] & 0xFF) | 0x100).substring(1, 3));
        }
        String hash = md5.toString();

        Request req = chain.request();

        HttpUrl newUrl = req.url().newBuilder()
                .addQueryParameter(TIMESTAMP, timestamp)
                .addQueryParameter(API_KEY, PUBLIC_KEY)
                .addQueryParameter(HASH, hash)
                .build();

        req = req.newBuilder().url(newUrl).build();
        return chain.proceed(req);

    }
}
