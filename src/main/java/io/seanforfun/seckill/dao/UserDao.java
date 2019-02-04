package io.seanforfun.seckill.dao;

import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.entity.vo.LoginVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Select("Select count(id) from user where username = #{username} and email = #{email}")
    Long getUserNumberByUsernameAndEmail(User user);

    @Select("Select id from user where username = #{username}")
    Long getUserIdByUsername(User user);

    // Inactivated users
    @Select("Select count(id) from user where activated = #{status}")
    int getUserNumberByUserStatus(@Param("status") int status);

    @Select("Select id, username, firstname, lastname, email, registertime from user where activated = #{status} " +
            " order by registerTime asc limit #{currentIndex}, #{perpage}")
    List<User> getUserListByUserStatus(@Param("status") int status,
                                       @Param("currentIndex") Long currentIndex,
                                       @Param("perpage") Long perpage);

    @Select("Select id, username, firstname, lastname, email, registertime from user where activated = #{status} " +
            " order by registerTime asc")
    List<User> getAllInactiveUserByStatus(int notActivated);
    /**
     * Update
     */
    @Update("Update user set lastLoginTime = #{currentTime} where id = #{id}")
    void updateLoginTime(@Param("id") Long id, @Param("currentTime") Long currentTime);

    @Update("Update user set password = #{password}, salt = #{salt} where username = #{username} and email = #{email}")
    void updateUserPasswordAndSalt(User user);

    @Update("Update user set activated = #{status} where id = #{id}")
    void updateUserActivateStatus(@Param("id") Long id, @Param("status") int newStatus);

    @Update("Update user set admin = #{adminStatus} where id = #{id}")
    void updateUserAdminStatus(@Param("id") Long id, @Param("adminStatus") boolean adminStatus);

    /**
     * Insert
     */
    @Insert("Insert into user (id, username, password, salt, admin, " +
            "country, state, zip, email, activated, registerTime, " +
            "lastLoginTime, lastModifiedTime, firstname, lastname) values" +
            " (#{id}, #{username}, #{password}, #{salt}, #{admin}, #{country}," +
            " #{state},#{zip}, #{email}, #{activated}, #{registerTime}, " +
            "#{lastLoginTime}, #{lastModifiedTime}, #{firstname}, #{lastname})")
    void saveRegisterUser(User user);

    /**
     * Delete
     */
}
