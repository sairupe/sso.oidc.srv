package com.syriana.sso.oidc.srv.controller;

import com.syriana.sso.oidc.srv.response.UserResVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syriana.zh
 * @date 2020/06/22
 */
@RestController
@RequestMapping("/resource")
public class TestController {

    @GetMapping("/getUser")
    public UserResVo getUser(){
        UserResVo user = new UserResVo();
        user.setUserName("userName");
        user.setUserPwd("userPwd");
        return user;
    }

}
