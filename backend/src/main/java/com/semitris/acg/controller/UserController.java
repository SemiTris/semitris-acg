package com.semitris.acg.controller;

import com.semitris.acg.entity.User;
import com.semitris.acg.service.UserService;
import com.semitris.acg.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Description 用户管理接口控制器
 * @Author SemiTris
 * @Date 2026年08月29日 17:15
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 新增用户
     *
     * @param user 用户实体（id、registerTime、updatedAt 无需前端传入）
     * @return 统一响应结果
     */
    @PostMapping
    public R<Void> addUser(@RequestBody User user) {
        boolean success = userService.addUser(user);
        return success ? R.success() : R.error("新增用户失败");
    }

    /**
     * 根据id删除用户
     *
     * @param id 用户id
     * @return 统一响应结果
     */
    @DeleteMapping("/{id}")
    public R<Void> removeUser(@PathVariable("id") int id) {
        boolean success = userService.removeUser(id);
        return success ? R.success() : R.error("删除用户失败");
    }

    /**
     * 修改用户信息
     *
     * @param id   用户id
     * @param user 用户实体（id、registerTime 无需前端传入）
     * @return 统一响应结果
     */
    @PutMapping("/{id}")
    public R<Void> editUser(@PathVariable("id") int id, @RequestBody User user) {
        user.setId((long) id);
        boolean success = userService.editUser(user);
        return success ? R.success() : R.error("修改用户失败");
    }

    /**
     * 根据id查询用户信息
     *
     * @param id 用户id
     * @return 统一响应结果
     */
    @GetMapping("/{id}")
    public R<User> getUser(@PathVariable("id") int id) {
        User user = userService.getUser(id);
        return user != null ? R.success(user) : R.error("用户不存在");
    }

    /**
     * 查询所有用户列表
     *
     * @return 统一响应结果
     */
    @GetMapping
    public R<User[]> getAllUser() {
        return R.success(userService.getAllUser());
    }

    /**
     * 多条件查询用户信息（用户名/昵称模糊查询）
     *
     * @param username 用户名
     * @param nickname 昵称
     * @return 统一响应结果
     */
    @GetMapping("/condition")
    public R<User[]> getUserByCondition(String username, String nickname) {
        return R.success(userService.getUserByCondition(username, nickname));
    }
}
