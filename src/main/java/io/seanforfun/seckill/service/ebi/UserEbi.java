package io.seanforfun.seckill.service.ebi;

import io.seanforfun.seckill.entity.domain.User;

import javax.servlet.http.HttpServletResponse;

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
}
