package io.seanforfun.seckill.service.ebi;

import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.entity.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/31 10:18
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public interface UserEbi {

    /**
     * Get user by id.
     * @param id
     * @return
     */
    User getUserById(Long id);

    /**
     * Get user instance from redis by token,
     * also need to extend the cookie activating time.
     * @param token
     * @return User instance
     * @throws Exception
     */
    User getUserByToken(HttpServletResponse response, String token) throws Exception;

    /**
     * Check user exists in Redis session.
     * @param token
     * @return
     */
    boolean userInRedisSession(String token);

    /**
     * Check if current user exists in the database.
     * @param user
     * @return true for user exist and false for user not exist
     */
    boolean exists(User user);

    /**
     * Check if current user has logged out. true for logout.
     * @param user
     * @param request
     * @param session
     */
    boolean checkLogout(User user, HttpServletRequest request, HttpSession session);

    /**
     * Upadate user password with username and email.
     * @param user
     */
    void updateUserPassword(User user);

    /**
     * Get number of unapproved user.
     * @return number of user
     */
    int getUnapprovedUserNumber();

    /**
     * Get user list by pagination.
     * @param userVo
     * @return List of user.
     */
    List<User> getUnapprovedUserListByPage(UserVo userVo);

    /**
     * Get all inactivate users.
     * @param userVo
     * @return
     */
    List<User> getUnapprovedUserList(UserVo userVo);

    /**
     * Update User activate status
     * @param user
     */
    void updateUserActivateStatus(User user, int newStatus);

    /**
     * Set user admin status.
     * @param user
     * @param adminStatus
     */
    void setAdminStatus(User user, boolean adminStatus);

    /**
     * Get all activated users.
     * @return
     */
    List<User> getActivatedUserList();
}
