package com.mercer.myblog.util;

import org.springframework.util.DigestUtils;

/**
 * @ Date:2019/8/21
 * Auther:Mercer
 * Auther:麻前程
 */
public class MD5Utils {
    public static void main(String[] args) {
       String md5 = DigestUtils.md5DigestAsHex("123".getBytes());
       System.out.println(md5);
    }
}
