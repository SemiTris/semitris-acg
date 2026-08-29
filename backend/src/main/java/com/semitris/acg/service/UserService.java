package com.semitris.acg.service;

import com.github.pagehelper.PageInfo;
import com.semitris.acg.entity.User;

/**
 * @ClassName UserService
 * @Description 用户服务接口
 * @Author SemiTris
 * @Date 2026年08月29日 16:50
 * @Version 1.0
 */

public interface UserService {
    //1.新增用户(密码做BCrypt加密)
    boolean addUser(User user);

    //2.删除用户(根据id删除用户)
    boolean removeUser(Long id);

    //3.修改用户信息
    boolean editUser(User user);

    //4.查询用户信息(根据id查询用户信息)
    User getUser(Long id);

    //5.分页多条件查询用户信息(根据用户名/昵称模糊查询)
    PageInfo<User> getUserPage(int pageNum, int pageSize, String username, String nickname);
}
