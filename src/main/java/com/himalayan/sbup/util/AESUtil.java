package com.himalayan.sbup.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * 类名
 * </p>
 * aes加密/解密   此处使用AES-128-ECB加密模式，key需要为16位。
 *
 * @author Administrator
 * @version 1.0
 */
public class AESUtil {

    // aesKey
    public static final String CDKEY = ",[AjiEWohgew/.?|";

    /**
     * <p>
     * 方法名
     * </p>
     * 加密
     *
     * @param sSrc 加密字符串
     * @param sKey 加密密钥
     * @return
     * @throws Exception
     */
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        sKey = keyValider(sKey);
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec("alwi2hvnaz.s923k".getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        return Base64.encode(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    /**
     * <p>
     * 方法名
     * </p>
     * 解密
     *
     * @param sSrc 解密文件
     * @param sKey 解密密钥
     * @return
     * @throws Exception
     */
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            sKey = keyValider(sKey);
            byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("alwi2hvnaz.s923k".getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decode(sSrc);// 先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                return new String(original);
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * 验证传入的CDKEY是否符合
     * @return
     */
    private static String keyValider(String sKey) {
        // 判断Key是否正确
        if (sKey == null) {
            throw new NullPointerException("sKey为空null !");
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            throw new RuntimeException("sKey长度必须为16位 !");
        }
        return sKey;
    }

    public static void main(String[] args) throws Exception {
        // 此处使用AES-128-ECB加密模式，key需要为16位。
        // String cKey = ",[AjiEWohgew/.?|";
        // 需要加密的字串
        // 加密
        String enString = AESUtil.Encrypt("15153157576a", CDKEY);
        System.out.println("加密后的字串是：" + enString);
        // 解密
        String DeString = AESUtil.Decrypt("GhZ/9cYBFxyyW+hRS3Wasg==", CDKEY);
        System.out.println("解密后的字串是：" + DeString);
    }
}
