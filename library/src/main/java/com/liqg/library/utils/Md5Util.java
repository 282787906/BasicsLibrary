/*
 * 创建人：李青刚
 * 创建时间：2013年11月19日 
 * 
 * 修改人：中文姓名
 * 修改时间：年-月-日 时
 * 修改内容：
 * 多点时，每点前面加数字编号“1.”，每点之间换行
 */
package com.liqg.library.utils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * MD5操作类
 */
public class Md5Util {
    String TAG = Md5Util.class.getSimpleName();
    static String m_result;

    /**
     * 字符串转换MD5
     *
     * @param str
     * @return MD5
     */
    public static String strToMD5(String str) {
        if (str == null) {
            m_result = "str is null";
            return null;
        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            m_result = "Exception：" + e.getMessage();
            return null;
        }
    }

    /**
     * 文件转换MD5
     *
     * @param path 文件路径
     * @return MD5
     */
    public static String fileToMD5(String path) {
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(new File(path));
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
            BigInteger bigInt = new BigInteger(1, digest.digest());
            String md5 = bigInt.toString(16).toUpperCase();
            while (md5.length() < 32) {
                md5 = 0 + md5;
            }
            return md5;
        } catch (Exception e) {
            e.printStackTrace();
            m_result = "Exception：" + e.getMessage();
            return null;
        }

    }
}
