package com.semitris.acg.service.impl;

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
    public boolean removeUser(int id) {
        return userMapper.deleteUserById(id) > 0;
    }

    /**
     * 修改用户信息
     * <p>若传入了密码则一并做 BCrypt 加密处理</p>
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
    public User getUser(int id) {
        return userMapper.selectUserById(id);
    }

    /**
     * 查询所有用户列表
     *
     * @return 用户数组
     */
    @Override
    public User[] getAllUser() {
        List<User> users = userMapper.selectAllUser();
        return users == null ? new User[0] : users.toArray(new User[0]);
    }

    /**
     * 多条件查询用户信息（用户名/昵称模糊查询）
     *
     * @param username 用户名
     * @param nickname 昵称
     * @return 用户数组
     */
    @Override
    public User[] getUserByCondition(String username, String nickname) {
        List<User> users = userMapper.selectUserByCondition(username, nickname);
        return users == null ? new User[0] : users.toArray(new User[0]);
    }
}
