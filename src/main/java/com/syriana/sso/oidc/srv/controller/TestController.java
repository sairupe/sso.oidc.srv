package com.syriana.sso.oidc.srv.controller;

import com.syriana.sso.oidc.srv.response.UserResVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author syriana.zh
 * @date 2020/06/22
 */
@RestController
@RequestMapping("/resource")
public class TestController {

    @GetMapping("/getUser")
    public UserResVo getUser(Principal principal){
        UserResVo user = new UserResVo();
        user.setUserName(principal.getName());
        user.setUserPwd("userPwd");
        return user;
    }

}
