package com.springmvc.entity;

public class ContractUser {
    private Integer userId;

    private String userCode;

    private String userName;

    private String userPassword;

    private String userCompanyCode;

    private String userTenantCode;

    private Integer userStatus;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserCompanyCode() {
        return userCompanyCode;
    }

    public void setUserCompanyCode(String userCompanyCode) {
        this.userCompanyCode = userCompanyCode;
    }

    public String getUserTenantCode() {
        return userTenantCode;
    }

    public void setUserTenantCode(String userTenantCode) {
        this.userTenantCode = userTenantCode;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }
}