package com.springmvc.common;

import com.springmvc.dao.ContractUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @version V1.0
 * @Title:
 * @Package
 * @Description:
 * @author: runoob
 * @date:
 */
@Component("myUserDetailService")
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private ContractUserMapper mapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        return mapper.selectByUserCode(s);
    }
}
