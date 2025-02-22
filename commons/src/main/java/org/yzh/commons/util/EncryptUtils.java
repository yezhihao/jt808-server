package org.yzh.commons.util;

import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 加密工具类
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class EncryptUtils {

    private static SecretKeySpec DefKey;

    private static IvParameterSpec DefInitVector;

    private static final String MODE = "AES/CTR/NoPadding";
//    private static final String MODE = "AES/CBC/PKCS5Padding";

    static {
        initial();
    }

    @SneakyThrows
    public static void initial() {
        initial(System.getProperty("user.name", "unknown"));
    }

    public static void initial(String secret) throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(secret.getBytes(StandardCharsets.UTF_8));

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128, random);

        //私钥 AES固定格式为128/192/256 bits.即：16/24/32bytes。DES固定格式为128bits，即8bytes。
        DefKey = new SecretKeySpec(keyGenerator.generateKey().getEncoded(), "AES");
        //初始向量 AES 为16bytes. DES 为8bytes
        DefInitVector = new IvParameterSpec(keyGenerator.generateKey().getEncoded());
    }

    @SneakyThrows
    public static byte[] encrypt(SecretKeySpec key, IvParameterSpec initVector, byte[] message) {
        Cipher cipher = Cipher.getInstance(MODE);
        cipher.init(Cipher.ENCRYPT_MODE, key, initVector);
        return cipher.doFinal(message);
    }

    public static byte[] encrypt(String key, String initVector, byte[] message) {
        return encrypt(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"), new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8)), message);
    }

    public static byte[] encrypt(byte[] message) {
        return encrypt(DefKey, DefInitVector, message);
    }

    @SneakyThrows
    public static byte[] decrypt(SecretKeySpec key, IvParameterSpec initVector, byte[] message) {
        Cipher cipher = Cipher.getInstance(MODE);
        cipher.init(Cipher.DECRYPT_MODE, key, initVector);
        return cipher.doFinal(message);
    }

    public static byte[] decrypt(String key, String initVector, byte[] message) {
        return decrypt(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"), new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8)), message);
    }

    public static byte[] decrypt(byte[] message) {
        return decrypt(DefKey, DefInitVector, message);
    }
}