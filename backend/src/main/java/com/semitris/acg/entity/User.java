package com.semitris.acg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName User
 * @Description 用户表实体类
 * @Author SemiTris
 * @Date 2026年08月27日 21:15
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;            //用户id
    private String username;    //用户名
    private String password;    //密码
    private String nickname;    //昵称
    private String avatar;      //头像url
    private Date registerTime;  //注册时间
    private Date updatedAt;     //信息更新时间

}