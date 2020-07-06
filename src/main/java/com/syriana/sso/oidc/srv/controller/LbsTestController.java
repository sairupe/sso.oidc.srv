package com.syriana.sso.oidc.srv.controller;

import com.syriana.sso.oidc.srv.response.UserResVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author syriana.zh
 * @date 2020/06/22
 */
@RestController
@RequestMapping("/lbs/resource")
public class LbsTestController {

    @PreAuthorize("hasAnyAuthority('p1')")
    @GetMapping("/getUser")
    public UserResVo getUser2(Principal principal){
        UserResVo user = new UserResVo();
        user.setUserName(principal.getName());
        user.setUserPwd("sso.oidc.srv - userPwd");
        return user;
    }
}
