package io.samos.nuls.controller;


import io.samos.nuls.common.RestResult;
import io.samos.nuls.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
// This means that this class is a Controller

@RestController
@RequestMapping(path="/passport") // Th
public class PassportController {

   @Autowired
   private UserService userService;

    @RequestMapping(path="/login")
    public RestResult login (@RequestParam String usernameOragentAddress
            , @RequestParam String password, HttpSession session) {

        RestResult result = userService.login(usernameOragentAddress,password,session);
        return result;
    }


    @GetMapping(path="/logout")
    public RestResult logout (HttpSession session) {
       RestResult result = userService.logout(session);
       return result;
    }
}
