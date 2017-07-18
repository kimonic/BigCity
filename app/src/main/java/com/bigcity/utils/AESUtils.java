package com.bigcity.utils;

import android.annotation.SuppressLint;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * * ==================================================
 * name:            AESUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/12
 * description：  AES加密工具类---加密方式---AES/CBC/PKCS5PADDING
 * history：
 * ---------------------encrypt(String key, String src)----加密返回16进制字符串
 * ---------------------encrypt(String key, String src)----解密返回文本
 * ---------------------
 * ---------------------
 * mark:              如果该类报错,则可能是字符编码不一致造成的
 * * ==================================================
 */

public class AESUtils {
    private final static String HEX = "0123456789ABCDEF";
    private final static int JELLY_BEAN_4_2 = 17;

    /**
     * 加密
     *
     * @param key 密钥
     * @param src 加密文本
     * @return 16进制文本
     */
    public static String encrypt(String key, String src) {
        byte[] rawKey;
        byte[] result;
        try {
            rawKey = getRawKey(key.getBytes("utf-8"));
            result = encrypt(rawKey, src.getBytes("utf-8"));
            return toHex(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "加密失败";
    }

    /**
     * 解密
     *
     * @param key       密钥
     * @param encrypted 待揭秘文本
     * @return 结果文本
     */
    public static String decrypt(String key, String encrypted)  {
        try {
            byte[] rawKey = getRawKey(key.getBytes("utf-8"));
            byte[] enc = toByte(encrypted);
            byte[] result = decrypt(rawKey, enc);
            return new String(result,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "解密失败";
    }

    /**
     * 获取256位的加密密钥
     *
     * @param seed 秘钥字节数组
     * @return 256位秘钥
     * @throws Exception 抛出异常
     */
    @SuppressLint("TrulyRandom")
    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");//键生成器
        SecureRandom sr;//安全随机
        // 在4.2以上版本中，SecureRandom获取方式发生了改变
        if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN_4_2) {
            sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        } else {
            sr = SecureRandom.getInstance("SHA1PRNG");
        }
        sr.setSeed(seed);
        // 256 bits or 128 bits,192bits
        kgen.init(256, sr);
        SecretKey skey = kgen.generateKey();
        return skey.getEncoded();
    }

    /**
     * 真正的加密过程
     *
     * @param key 加密秘钥
     * @param src 加密文本
     * @return 返回加密后的字节数组
     * @throws Exception 抛出异常
     */
    private static byte[] encrypt(byte[] key, byte[] src) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
//        Cipher cipher = Cipher.getInstance("AES");//密码加密方式
//        Cipher cipher = Cipher.getInstance("AES/CFB8/PKCS5PADDING");//密码加密方式
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");//密码加密方式
//        Cipher cipher = Cipher.getInstance("AES/NONE/PKCS5PADDING");//密码加密方式
//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//密码加密方式,算法/模式/补码方式
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec,iv);
        return cipher.doFinal(src);
    }

    /**
     * 真正的解密过程
     *
     * @param key       秘钥数组
     * @param encrypted 密文数组
     * @return 解密后后的文本数组
     * @throws Exception 抛出异常
     */
    private static byte[] decrypt(byte[] key, byte[] encrypted)
            throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
//        Cipher cipher = Cipher.getInstance("AES");
//        Cipher cipher = Cipher.getInstance("AES/CFB8/PKCS5PADDING");//密码加密方式
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");//密码加密方式
//        Cipher cipher = Cipher.getInstance("AES/NONE/PKCS5PADDING");//密码加密方式
//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//密码加密方式
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.DECRYPT_MODE, skeySpec,iv);
        return cipher.doFinal(encrypted);
    }

    /**
     * 文本转化为16进制
     */
    public static String toHex(String txt) {
        try {
            return toHex(txt.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "编码错误";
    }

    /**
     * 16进制转化为文本
     */
    public static String fromHex(String hex) {
        try {
            return new String(toByte(hex),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "编码错误";
    }

    /**
     * 16进制字符串转化为字节数组
     */
    private static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }

    /**
     * 字节数组转化为16进制字符串
     */
    private static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (Byte mbuf:buf) {
            appendHex(result, mbuf);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}

