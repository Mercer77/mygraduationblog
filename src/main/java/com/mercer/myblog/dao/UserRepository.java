package com.mercer.myblog.dao;

import com.mercer.myblog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ Date:2019/8/18
 * Auther:Mercer
 * Auther:麻前程
 */
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUsernameAndPassword(String username,String password);
}
