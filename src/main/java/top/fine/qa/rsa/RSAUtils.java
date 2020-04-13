package top.fine.qa.rsa;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtils {
    /**
     * 密钥长度 于原文长度对应 以及越长速度越慢
     */
    private final static int KEY_SIZE = 1024;
    /**
     * 公钥与私钥
     */
    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDSMOrr1owvuVEcC+56QH6+08LmUo+iJ/dwW7u2q4o8M6XK0G7hxiYFYCPn0UpnZ+E2U1BkHWBMfSByXLiymb4CvSv6Z8zl/GItukqUiRULTjn7bMNVuRU1tiJVeUkx+DcQ1D8CbVLtFASco1cJ8BSYCa+He7pfvF3snzyo2BM1NQIDAQAB";
    private static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANIw6uvWjC+5URwL7npAfr7TwuZSj6In93Bbu7arijwzpcrQbuHGJgVgI+fRSmdn4TZTUGQdYEx9IHJcuLKZvgK9K/pnzOX8Yi26SpSJFQtOOftsw1W5FTW2IlV5STH4NxDUPwJtUu0UBJyjVwnwFJgJr4d7ul+8XeyfPKjYEzU1AgMBAAECgYA7OJ8n/ZYX6C3LKi1x8nQDjiSOwlpHxNz5ok78Q8658Y9X2mwzXqHdx9219gjxo5oXtVekLYaxZlva/PbpE1ouZmufXqW8PIgGBmhU/ml0RvRf7Ae14z5HKtZ5Ns7eXW/6oT4rca0FBqRx7eL6rWrkBJfPX7Te9R9L5Yqjo5RY7QJBAPZ6IBkHSxBYrXeYmWJBziB4Az9wyjprzY9QZNspHL/nmnL6bcml/DLo4RTytchFW570HKCBuBdR4M10MV7vxT8CQQDaT+Rlod1+Wtj/miEZ5csdB1G4LRlk5mct9w2lJzv+VwgBSiwUJfbqNeLev9LhpkL7VjJBjHCo8HhkJwfvCeSLAkBhvdpesoK078WSiMRCr7/TJmR7UvoPItL4gzQLqQkKnQilkePZtGYHtkwT54/o5IBJz5pOmEJtlP2l2+78K+83AkEAhCYuVFMaF514k6jB9sJCJC1FC17Ja2mI9asB6IIKb9hQ8S7r5bphb770uO+XAJ67FxdDTqhrnegKiF2UEN+FJwJAJ223ZBjU3ngi+QIFFM9ZEF3ZNsha1Lr54Nu8LvzAHWBPAAqByiXVAiyGXgViF8o+x9hWAYjjNnXGFxuroYjgew==";

    /**
     * 随机生成密钥对
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器
        keyPairGen.initialize(KEY_SIZE, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        // 得到私钥字符串
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    private static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * 公钥加密
     *
     * @param content 加密内容
     * @return
     * @throws Exception
     */
    public static String encrypt(String content) throws Exception {
        return encrypt(content, publicKey);
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    private static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.getDecoder().decode(str);
        //base64编码的私钥
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    /**
     * 私钥解密
     *
     * @param secret 密文
     * @return
     * @throws Exception
     */
    public static String decrypt(String secret) throws Exception {
        return decrypt(secret, privateKey);
    }


    public static void main(String[] args) throws Exception {
        long temp = System.currentTimeMillis();
        //生成公钥和私钥
//        genKeyPair();
        //加密字符串
//        System.out.println("公钥:" + keyMap.get(0));
//        System.out.println("私钥:" + keyMap.get(1));
//        System.out.println("生成密钥消耗时间:" + (System.currentTimeMillis() - temp) / 1000.0 + "秒");
//        String message = "RSA测试ABCD~!@#$";
//        System.out.println("原文:" + message);
//        temp = System.currentTimeMillis();
//        String messageEn = encrypt(message, publicKey);
//        System.out.println("密文:" + messageEn);
//        System.out.println("加密消耗时间:" + (System.currentTimeMillis() - temp) / 1000.0 + "秒");
//        temp = System.currentTimeMillis();
//        String messageDe = decrypt(messageEn, privateKey);
//        System.out.println("解密:" + messageDe);
//        System.out.println("解密消耗时间:" + (System.currentTimeMillis() - temp) / 1000.0 + "秒");

        String content = "12345xiao";
        String encrypt = encrypt(content);
        System.out.println("密文：" + encrypt);
        String decrypt = decrypt(encrypt);
        System.out.println();
        System.out.println("明文：" + decrypt);
    }
}