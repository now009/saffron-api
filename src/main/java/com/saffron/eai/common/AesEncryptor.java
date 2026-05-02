package com.saffron.eai.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class AesEncryptor {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    @Value("${eai.encrypt.aes-key:changeme-32-byte-key-placeholder}")
    private String aesKey;

    public String encrypt(String data) {
        try {
            byte[] keyBytes = aesKey.getBytes("UTF-8");
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec iv = new IvParameterSpec(keyBytes, 0, 16);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes("UTF-8")));
        } catch (Exception e) {
            throw new EaiAdapterException("AES 암호화 실패: " + e.getMessage(), e);
        }
    }

    public String decrypt(String data) {
        try {
            byte[] keyBytes = aesKey.getBytes("UTF-8");
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec iv = new IvParameterSpec(keyBytes, 0, 16);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            return new String(cipher.doFinal(Base64.getDecoder().decode(data)), "UTF-8");
        } catch (Exception e) {
            throw new EaiAdapterException("AES 복호화 실패: " + e.getMessage(), e);
        }
    }
}
