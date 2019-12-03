package io.samos.nuls.service;

import io.samos.nuls.common.RestResult;

import javax.servlet.http.HttpSession;

public interface UserService {
    RestResult createUsers();
    RestResult changeUsername(int userId,String username);
    RestResult changePassword(String username,String password1,String password2);
    RestResult login(String usernameOragentAddress, String password, HttpSession httpSession);
    RestResult logout(HttpSession httpSession);
}
