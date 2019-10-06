package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ldapController {

    @RequestMapping(value="/authority/password")
    public String goPasswordChangePage()
    {
        return "changePassword";
    }
    @RequestMapping(value = "/authority/modifypassword",produces ="application/json;charset=UTF-8")
    public @ResponseBody String changeLDAPPassword(@RequestParam("originPassword")String originPassword,@RequestParam("newPassword")String newPassword){
        System.out.println("the originPassword is:"+originPassword);
        System.out.println("the newPassword is:"+newPassword);
        String resultString="{\"result\":\"success\"}";
        return resultString;
    }

}
