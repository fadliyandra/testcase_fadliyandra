package com.example.casefitnesscenter.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Encryptor {
    public static final int ITERATIONS = 65536;
    // Desired length of the derived key
    private static final int KEY_LENGTH = 256;
    private static final byte[] FIXED_KEY = "1234567890123456".getBytes(StandardCharsets.UTF_8);
    private static final byte[] FIXED_IV = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};



    private Encryptor() {
    }

    public static String encryptToAES(String requestBody) {
        try {
            SecretKey secretKey = new SecretKeySpec(FIXED_KEY, "AES");

            GCMParameterSpec parameterSpec = new GCMParameterSpec(96, FIXED_IV);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

            byte[] encryptedData = cipher.doFinal(requestBody.getBytes());

            byte[] encode = Base64.getEncoder().encode(encryptedData);
            return new String(encode);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static String encryptCreditCard(String requestBody) {
        try {
            var messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] requestBytes = requestBody.getBytes(StandardCharsets.UTF_8);
            byte[] hashInBytes = messageDigest.digest(requestBytes);

            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashInBytes) {
                stringBuilder.append(String.format("%02x", b));
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String encryptAESKey(String password, byte[] retrievedIV, byte[] bytesEncryptedData, byte[] salt) {
        try {
            SecretKey secretKeySpec = generateSecretKey(password, salt);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, retrievedIV);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, gcmParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(bytesEncryptedData);

            byte[] encode = Base64.getUrlEncoder().encode(decryptedBytes);
            return new String(encode);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException(e);
        }
    }

    static SecretKeySpec generateSecretKey(String password, byte[] salt) {
        try {
            KeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            SecretKey secretKey = keyFactory.generateSecret(pbeKeySpec);

            return new SecretKeySpec(secretKey.getEncoded(), "AES");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
