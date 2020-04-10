package com.mercer.myblog.service;

import com.mercer.myblog.entity.User;

/**
 * @ Date:2019/8/18
 * Auther:Mercer
 * Auther:麻前程
 */
public interface UserService {
    User checkUser(String username,String password);

    User save(User user);
}
