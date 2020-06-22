package com.syriana.sso.oidc.srv.response;

/**
 * @author syriana.zh
 * @date 2020/06/22
 */
public class UserResVo {

    private String userName;

    private String userPwd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
