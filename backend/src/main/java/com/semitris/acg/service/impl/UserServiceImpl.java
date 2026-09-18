package com.semitris.acg.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.semitris.acg.entity.User;
import com.semitris.acg.mapper.UserMapper;
import com.semitris.acg.service.UserService;
import com.semitris.acg.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description 用户服务实现类
 * @Author SemiTris
 * @Date 2026年08月29日 16:51
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 新增用户
     * <p>密码入库前使用 BCrypt 加密（数据库规范禁止 MD5）</p>
     *
     * @param user 用户实体
     * @return 是否新增成功
     */
    @Override
    public boolean addUser(User user) {
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty()) {
            return false;
        }
        //密码BCrypt加密后入库
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        return userMapper.insertUser(user) > 0;
    }

    /**
     * 根据id删除用户
     *
     * @param id 用户id
     * @return 是否删除成功
     */
    @Override
    public boolean removeUser(Long id) {
        return userMapper.deleteUserById(id) > 0;
    }

    /**
     * 修改用户信息
     * <p>若传入了密码则一并做 BCrypt 加密处理；空值字段不更新（动态 SQL）</p>
     *
     * @param user 用户实体（必须携带id）
     * @return 是否修改成功
     */
    @Override
    public boolean editUser(User user) {
        if (user == null || user.getId() == null) {
            return false;
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(PasswordUtil.encode(user.getPassword()));
        }
        return userMapper.updateUser(user) > 0;
    }

    /**
     * 根据id查询用户信息
     *
     * @param id 用户id
     * @return 用户实体，不存在时返回null
     */
    @Override
    public User getUser(Long id) {
        return userMapper.selectUserById(id);
    }

    /**
     * 分页多条件查询用户（用户名/昵称模糊查询）
     *
     * @param pageNum  页码（从1开始）
     * @param pageSize 每页条数
     * @param username 用户名（可选）
     * @param nickname 昵称（可选）
     * @return 分页结果 PageInfo
     */
    @Override
    public PageInfo<User> getUserPage(int pageNum, int pageSize, String username, String nickname) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.selectUserByCondition(username, nickname);
        return new PageInfo<>(users);
    }

    /**
     * 登录
     * <p>按 username 精确查库，BCrypt matches 比对密码；成功返回用户信息（password 置空不回传），失败返回 null</p>
     *
     * @param username 账号
     * @param password 明文密码
     * @return 用户信息（不含密码），或登录失败返回 null
     */
    @Override
    public User login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return null;
        }
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return null;
        }
        //BCrypt 比对（数据库规范禁止 MD5）
        if (!PasswordUtil.matches(password, user.getPassword())) {
            return null;
        }
        //响应不回传密码
        user.setPassword(null);
        return user;
    }
}
