package com.semitris.acg.mapper;

import com.semitris.acg.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    //1.新增用户
    int insertUser(User user);

    //2.删除用户(根据id删除用户)
    int deleteUserById(Long id);

    //3.修改用户信息
    int updateUser(User user);

    //4.查询用户信息(根据id查询用户)
    User selectUserById(Long id);

    //5.多条件查询用户信息(用户名/昵称模糊查询，分页由PageHelper完成)
    List<User> selectUserByCondition(@Param("username") String username,
                                     @Param("nickname") String nickname);
}
