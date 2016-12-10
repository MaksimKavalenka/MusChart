package com.muschart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.muschart.entity.UserEntity;
import com.muschart.service.dao.UserServiceDAO;

@Service("userDetailsSecurityService")
public class UserDetailsSecurityService implements UserDetailsService {

    @Autowired
    private UserServiceDAO userService;

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.getUserByLogin(username);
    }

}