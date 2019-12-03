package io.samos.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class SafeUtil {
    public static  String salt = "samosnuls";
    public static  String getPasswd(String password) {
          return DigestUtils.sha1Hex((password+salt).getBytes());
    }

    public static void main(String[] args) {
        System.out.println(getPasswd("yuhangbin"));
    }
}
