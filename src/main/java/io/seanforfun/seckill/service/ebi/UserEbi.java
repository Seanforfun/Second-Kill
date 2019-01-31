package io.seanforfun.seckill.service.ebi;

import io.seanforfun.seckill.entity.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/31 10:18
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public interface UserEbi {

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
     * @return
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
}
