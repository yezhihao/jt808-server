package org.yzh.commons.util;

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
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class EncryptUtils {

    private static volatile SecretKeySpec DefKey;

    private static volatile IvParameterSpec DefInitVector;

    private static final String MODE = "AES/CTR/NoPadding";
//    private static final String MODE = "AES/CBC/PKCS5Padding";

    static {
        initial();
    }

    /**
     * 初始化密钥
     * @param privateKey 私钥 AES固定格式为128/192/256 bits.即：16/24/32bytes。DES固定格式为128bits，即8bytes。
     * @param initVector 初始向量 AES 为16bytes. DES 为8bytes
     */
    public static void initial(byte[] privateKey, byte[] initVector) {
        DefKey = new SecretKeySpec(privateKey, "AES");
        DefInitVector = new IvParameterSpec(initVector);
    }

    public static void initial() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

            SecureRandom keyRandom = SecureRandom.getInstance("SHA1PRNG");
            keyRandom.setSeed("test~!@_128".getBytes(StandardCharsets.UTF_8));
            keyGenerator.init(128, keyRandom);
            byte[] key = keyGenerator.generateKey().getEncoded();

            SecureRandom ivRandom = SecureRandom.getInstance("SHA1PRNG");
            ivRandom.setSeed("test~!@_128".getBytes(StandardCharsets.UTF_8));
            keyGenerator.init(128, ivRandom);
            byte[] initVector = keyGenerator.generateKey().getEncoded();

            initial(key, initVector);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("初始化密钥失败", e);
        }
    }

    public static byte[] encrypt(SecretKeySpec key, IvParameterSpec initVector, byte[] message) {
        try {
            Cipher cipher = Cipher.getInstance(MODE);
            cipher.init(Cipher.ENCRYPT_MODE, key, initVector);

            return cipher.doFinal(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encrypt(String key, String initVector, byte[] message) {
        return encrypt(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"), new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8)), message);
    }

    public static byte[] encrypt(byte[] message) {
        return encrypt(DefKey, DefInitVector, message);
    }

    public static byte[] decrypt(SecretKeySpec key, IvParameterSpec initVector, byte[] message) {
        try {
            Cipher cipher = Cipher.getInstance(MODE);
            cipher.init(Cipher.DECRYPT_MODE, key, initVector);

            return cipher.doFinal(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] decrypt(String key, String initVector, byte[] message) {
        return decrypt(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"), new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8)), message);
    }

    public static byte[] decrypt(byte[] message) {
        return decrypt(DefKey, DefInitVector, message);
    }
}