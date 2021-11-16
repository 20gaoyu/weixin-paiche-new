package cn.gy.util;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
public class DigestUtil {
    private static final String DEFAULT_ENCODE = "UTF-8";
    private static final String DEFAULT_KEY = "ctg-hbase1234#!@";

    public static String encryptAes(String sSrc) {
        return encrypt(sSrc, "AES");
    }

    public static String decryptAes(String sSrc) {
        return decrypt(sSrc, "AES");
    }

    public static String encryptDes(String sSrc) {
        return encrypt(sSrc, "DES");
    }

    public static String decryptDes(String sSrc) {
        return decrypt(sSrc, "DES");
    }

    public static String encrypt(String sSrc, String algorithm) {
        return encrypt(sSrc, algorithm, DEFAULT_KEY);
    }

    public static String decrypt(String sSrc, String algorithm) {
        return decrypt(sSrc, algorithm, DEFAULT_KEY);
    }

    public static String encrypt(String sSrc, String algorithm, String key) {
        try {
            Cipher cipher = buildCipher(Cipher.ENCRYPT_MODE, algorithm, key);
            if (cipher == null) {
                return sSrc;
            }
            byte[] result = cipher.doFinal(sSrc.getBytes(DEFAULT_ENCODE));
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            log.error(algorithm + "加密异常", e);
        }
        return sSrc;
    }

    public static String decrypt(String sSrc, String algorithm, String key) {
        try {
            Cipher cipher = buildCipher(Cipher.DECRYPT_MODE, algorithm, key);
            if (cipher == null) {
                return sSrc;
            }
            byte[] result = cipher.doFinal(Base64.getDecoder().decode(sSrc));
            return new String(result, DEFAULT_ENCODE);
        } catch (Exception e) {
            log.error(algorithm + "解密异常", e);
        }
        return sSrc;
    }

    private static Cipher buildCipher(int aesMode, String algorithm, String key) {
        Cipher cipher = null;
        switch (algorithm) {
            case "AES":
                cipher = buildAesCipher(aesMode, key);
                break;
            /*case "DES":
                cipher = buildDesCipher(aesMode, key);
                break;*/
        }
        return cipher;
    }

    private static Cipher buildAesCipher(int aesMode, String key) {
        Cipher cipher = null;
        try {
            //1.生成key
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes(DEFAULT_ENCODE));
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] keyBytes = secretKey.getEncoded();
            //2.key的转换
            Key secretKeySpec = new SecretKeySpec(keyBytes, "AES");
            //3.加密
            cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(aesMode, secretKeySpec);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException e) {
            log.error("AES Cipher获取失败", e);
        }
        return cipher;
    }

    /*private static Cipher buildDesCipher(int aesMode, String key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key.getBytes(DEFAULT_ENCODE));
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密钥初始化Cipher对象
        cipher.init(aesMode, securekey, sr);
        return cipher;
    }*/

    /**
     * JASYPT DES算法
     */
    private static final String JASYPT_DEFAULT_KEY = "DES_YUnCTG@ai-verify";
    private static final int JASYPT_POOL_SIZE = 10;

    public static String encryptDesWithPBE(String sSrc) {
        return encryptDesWithPBE(sSrc, JASYPT_DEFAULT_KEY);
    }

    public static String decryptDesWithPBE(String sSrc) {
        return decryptDesWithPBE(sSrc, JASYPT_DEFAULT_KEY);
    }

    public static String encryptDesWithPBE(String sSrc, String key) {
        try {
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
            config.setPassword(key);
            config.setAlgorithm("PBEWithMD5AndDES");
            config.setPoolSize(JASYPT_POOL_SIZE);
            encryptor.setConfig(config);
            return encryptor.encrypt(sSrc);
        } catch (Exception e) {
            log.error("JASYPT DES加密异常", e);
        }
        return sSrc;
    }

    public static String decryptDesWithPBE(String sSrc, String key) {
        try {
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
            config.setPassword(key);
            config.setAlgorithm("PBEWithMD5AndDES");
            encryptor.setConfig(config);
            return encryptor.decrypt(sSrc);
        } catch (Exception e) {
            log.error("JASYPT DES解密异常", e);
        }
        return sSrc;
    }

    public static void main(String[] args) {

    }
}
