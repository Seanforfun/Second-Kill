package io.seanforfun.seckill.service.ebi;

import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.entity.vo.LoginVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/27 20:32
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public interface LoginEbi {

    /**
     * @param request
     * @param response
     * @param loginVo
     * @return Return user information if success.
     * Need to save user information into cookie for remember me.
     * @throws Exception
     */
    User login(HttpServletRequest request, HttpServletResponse response, LoginVo loginVo) throws Exception;

    /**
     * Remove distribute session and invalid current cummunication session.
     * @param token Token for getting distributed session for current user.
     * @param session Single end session for current connection.
     * @throws Exception
     */
    void logout(String token, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
