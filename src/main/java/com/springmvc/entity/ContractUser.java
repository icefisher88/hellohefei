package com.springmvc.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContractUser implements Serializable, UserDetails {
    private Integer userId;

    private String userCode;

    private String userName;

    private String userPassword;

    private String userRole;

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

    public String getUserRole(){return userRole;}

    public void setUserRole(String userRole){this.userRole=userRole;}

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> autorityList = new ArrayList<GrantedAuthority>();
        if(userRole!=null) {
            GrantedAuthority autority = new SimpleGrantedAuthority("ROLE_USER");
            autorityList.add(autority);
        }
        if(userRole.equals("1")) {
            GrantedAuthority autority = new SimpleGrantedAuthority("ROLE_ADMIN");
            autorityList.add(autority);
        }
        System.out.println("userCode is: "+userCode+",userRole is:"+userRole);
        return autorityList;
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(userStatus==0)
            return false;
        else
            return true;
    }
}