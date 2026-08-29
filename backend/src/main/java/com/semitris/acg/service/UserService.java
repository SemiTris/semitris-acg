package com.semitris.acg.service;

import com.semitris.acg.entity.User;

/**
 * @ClassName UserService
 * @Description UserService
 * @Author SemiTris
 * @Date 2026年08月29日 16:50
 * @Version 1.0
 */

public interface UserService {
    //1.新增用户(密码暂时做MD5加密)
    boolean addUser(User user);

    //2.删除用户(根据id删除用户)
    boolean removeUser(int id);

    //3.修改用户信息
    boolean editUser(User user);

    //4.查询用户信息(根据id查询用户信息)
    User getUser(int id);

    //5.查询所有用户列表
    User[] getAllUser();

    //6.多条件查询用户信息(根据用户名/昵称模糊查询)
    User[] getUserByCondition(String username, String nickname);
}