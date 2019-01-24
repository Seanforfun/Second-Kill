package io.seanforfun.seckill.dao;

import io.seanforfun.seckill.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

    @Insert("Insert into user (id, name) values (#{id}, #{name})")
    int insert(User user);
}
