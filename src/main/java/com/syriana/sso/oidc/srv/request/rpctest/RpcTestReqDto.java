package com.syriana.sso.oidc.srv.request.rpctest;

import lombok.Data;

/**
 * @author syriana.zh
 * @date 2020/11/03
 */
@Data
public class RpcTestReqDto {

    private String userName;

    private String userPwd;

    private Long empNo;

}
