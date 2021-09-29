package com.jx.normal;

import android.util.Base64;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;

public class 测试sha256 {
    @Test
    public void main() {
        String sha256 = getSHA256("123456");
        System.out.println(sha256);
    }

    /**
     * 利用java原生的类实现SHA256加密
     *
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256(String str) {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodestr;
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    /**
     * sha256withrsa
     */
    private static String signBySHA256WithRSA(String content, String key) {
        String str = "";
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            PrivateKey privateKey = getPrivateKey(key);
            //signature.initSign(privateKey);
            signature.update(content.getBytes(StandardCharsets.UTF_8));
            str = Base64.encodeToString(signature.sign(), 0);
        } catch (Exception e) {
        }
        return str;
    }

    /**
     * string转私钥对象
     *
     * @param key
     * @return
     */
    private static PrivateKey getPrivateKey(String key) {
        PrivateKey privateKey = null;
        return privateKey;
    }
}
