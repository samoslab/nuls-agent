package io.samos.utils;

import io.samos.nuls.entity.User;

import javax.servlet.http.HttpServletRequest;

public class RightUtil {

    public static boolean hasRight(User u, HttpServletRequest req) {

        return true;
    }
}
