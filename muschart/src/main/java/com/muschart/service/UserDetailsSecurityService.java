package com.muschart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.muschart.entity.UserEntity;
import com.muschart.service.database.dao.UserDatabaseServiceDAO;

@Service("userDetailsSecurityService")
public class UserDetailsSecurityService implements UserDetailsService {

    @Autowired
    private UserDatabaseServiceDAO userService;

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.getUserByLogin(username);
    }

}