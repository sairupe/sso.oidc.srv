package com.syriana.sso.oidc.srv.controller;

import com.syriana.sso.oidc.srv.request.rpctest.RpcTestReqDto;
import com.syriana.sso.oidc.srv.response.rpctest.RpcTestResDto;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syriana.zh
 * @date 2020/06/22
 */
@Slf4j
@RestController
@RequestMapping("/rpc")
public class RpcTestController {

    @PostMapping("/test")
    public RpcTestResDto rpc(@RequestBody RpcTestReqDto queryDto) {
        log.info(new DateTime().toString());
        RpcTestResDto result = new RpcTestResDto();
        if (queryDto != null) {
            result.setUserName(queryDto.getUserName());
            result.setUserPwd(queryDto.getUserPwd());
            result.setEmpNo(queryDto.getEmpNo());
        }
        return result;
    }
}
