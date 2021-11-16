package cn.gy.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @Author : gaoyu
 * @CreateTime: 2021/11/3 11:30
 */
public class test {
    public static void main(String[] args) {
        String salt = RandomStringUtils.randomAlphanumeric(10);
        System.out.println(salt);
        System.out.println(DigestUtils.md5Hex(DigestUtils.md5Hex("admin@123") + salt));
    }
}
