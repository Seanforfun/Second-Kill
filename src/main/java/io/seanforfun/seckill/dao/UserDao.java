package io.seanforfun.seckill.dao;

import io.seanforfun.seckill.entity.domain.User;
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
    @Select("Select * from user where id = #{id}")
    User getById(@Param("id") long id);

    @Select("Select password, salt from user where username = #{username}")
    User getUserIDPasswordByUserName(@Param("username") String username);

    @Update("Update user set lastLoginTime = #{currentTime} where id = #{id}")
    void updateLoginTime(@Param("id") Long id, @Param("currentTime") Long currentTime);
}
