package io.seanforfun.seckill.dao;

import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.entity.vo.LoginVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/24 12:45
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Mapper
@Repository
public interface UserDao {
    /**
     * Select
     */
    @Select("Select * from user where id = #{id}")
    User getById(@Param("id") long id);

    @Select("Select id, username, password, salt, admin, activated from user where username = #{username}")
    User getUserInfoByLoginVo(LoginVo loginVo);

    @Select("Select id, username, password, salt from user where username = #{username}")
    User getUserIDPasswordByUserName(@Param("username") String username);

    @Select("Select count(id) from user where username = #{username}")
    Long getUserNumByUsername(@Param("username") String username);

    /**
     * Update
     */
    @Update("Update user set lastLoginTime = #{currentTime} where id = #{id}")
    void updateLoginTime(@Param("id") Long id, @Param("currentTime") Long currentTime);

    /**
     * Insert
     */
    @Insert("Insert into user (username, password, salt, admin, country, state, zip, email, activated, registerTime, lastLoginTime, lastModifiedTime) values (#{username}, #{password}, #{salt}, #{admin}, #{country}, #{state},#{zip}, #{email}, #{activated}, #{registerTime}, #{lastLoginTime}, #{lastModifiedTime})")
    void saveRegisterUser(User user);


    /**
     * Delete
     */
}
