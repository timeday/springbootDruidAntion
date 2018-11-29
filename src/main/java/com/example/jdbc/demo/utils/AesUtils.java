package com.example.jdbc.demo.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AesUtils {

    private static final String IV = "51C2E2ADA09216Bu";
    private static final String KEY = "PBBK0jSxCHAruR5Sz7gUrQ==";

    public static String encrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec key = new SecretKeySpec(Base64.getDecoder().decode("JAAK0jSxCHAruR5Sz7gUrQ=="), "AES");
            IvParameterSpec iv = new IvParameterSpec("40C2E2ADA09216Bu".getBytes("UTF-8"));
            cipher.init(1, key, iv);
            byte[] result = cipher.doFinal(content.getBytes());
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception var5) {
            throw new RuntimeException("AES encrypt error", var5);
        }
    }

    public static String decrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("40C2E2ADA09216Bu".getBytes("UTF-8"));
            SecretKeySpec key = new SecretKeySpec(Base64.getDecoder().decode("JAAK0jSxCHAruR5Sz7gUrQ=="), "AES");
            cipher.init(2, key, iv);
            byte[] result = Base64.getDecoder().decode(content);
            return new String(cipher.doFinal(result));
        } catch (Exception var5) {
            throw new RuntimeException("AES decrypt error", var5);
        }
    }

    private AesUtils() {
    }
}
