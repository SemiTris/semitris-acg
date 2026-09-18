package com.semitris.acg.controller;

import com.github.pagehelper.PageInfo;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    @PostMapping("/add")
    public R<Void> addUser(@RequestBody User user) {
        boolean success = userService.addUser(user);
        return success ? R.success() : R.error("新增用户失败");
    }

    /**
     * 登录（按 username 精确查 + BCrypt matches 比对；成功返回不含密码的用户信息）
     *
     * @param user 登录请求（仅 uusername/password 参与；契约为 { "username","password" }）
     * @return 统一响应结果（成功 R.success("登录成功", user)，失败 R.error("账号或密码错误")）
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody User user) {
        User loggedIn = userService.login(user.getUsername(), user.getPassword());
        return loggedIn != null
                ? R.success("登录成功", loggedIn)
                : R.error("账号或密码错误");
    }

    /**
     * 根据id删除用户
     *
     * @param id 用户id
     * @return 统一响应结果
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> removeUser(@PathVariable("id") Long id) {
        boolean success = userService.removeUser(id);
        return success ? R.success() : R.error("删除用户失败");
    }

    /**
     * 修改用户信息
     *
     * @param user 用户实体（必须携带 id；空字段不更新）
     * @return 统一响应结果
     */
    @PutMapping("/update")
    public R<Void> editUser(@RequestBody User user) {
        boolean success = userService.editUser(user);
        return success ? R.success() : R.error("修改用户失败");
    }

    /**
     * 根据id查询用户信息
     *
     * @param id 用户id
     * @return 统一响应结果
     */
    @GetMapping("/findById/{id}")
    public R<User> getUser(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        return user != null ? R.success(user) : R.error("用户不存在");
    }

    /**
     * 分页多条件查询用户信息（用户名/昵称模糊查询）
     *
     * @param pageNum  页码，默认1
     * @param pageSize 每页条数，默认10
     * @param username 用户名（可选）
     * @param nickname 昵称（可选）
     * @return 统一响应结果（携带分页信息 PageInfo）
     */
    @GetMapping("/page")
    public R<PageInfo<User>> getUserPage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "nickname", required = false) String nickname) {
        return R.success(userService.getUserPage(pageNum, pageSize, username, nickname));
    }
}
