

package com.udgrp.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author kejw
 * @version V1.0
 * @Project ud-hbase-search
 * @Description: TODO
 * @date 2017/10/17
 */
public class HashMD5 {
    public static String MD5(String key) {
        return stringHexa(digest(key, "MD5"));
    }

    protected static String stringHexa(byte[] bytes) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
            int parteBaixa = bytes[i] & 0xf;
            if (parteAlta == 0) {
                s.append('0');
            }
            s.append(Integer.toHexString(parteAlta | parteBaixa));
        }
        return s.toString();
    }

    protected static byte[] digest(String frase, String algoritmo) {
        try {
            MessageDigest md = MessageDigest.getInstance(algoritmo);
            md.update(frase.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
