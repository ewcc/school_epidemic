package com.ew.school_epidemic.utils;

import java.security.MessageDigest;

/**
 * @author ew
 * @date 2020/9/27 15:21
 */
public class MD5Util {
    /***
     * MD5加密 生成32位md5码
     *
     * @param
     * @return 返回32位md5码
     */
    public static String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 测试
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String str = "123456";
        System.out.println(MD5Util.md5Encode(str));
    }
}
