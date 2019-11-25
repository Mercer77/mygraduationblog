package com.mercer.myblog.service.impl;

import com.mercer.myblog.dao.UserRepository;
import com.mercer.myblog.po.User;
import com.mercer.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @ Date:2019/8/18
 * Auther:Mercer
 * Auther:麻前程
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findUserByUsernameAndPassword(username, DigestUtils.md5DigestAsHex(password.getBytes()));
        return user;
    }
}
