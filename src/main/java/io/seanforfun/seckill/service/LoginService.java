package io.seanforfun.seckill.service;

import com.sun.deploy.net.HttpResponse;
import io.seanforfun.seckill.dao.UserDao;
import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.entity.vo.LoginVo;
import io.seanforfun.seckill.exceptions.GlobalException;
import io.seanforfun.seckill.redis.RedisService;
import io.seanforfun.seckill.redis.UserKey;
import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.service.ebi.LoginEbi;
import io.seanforfun.seckill.utils.MD5Utils;
import io.seanforfun.seckill.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/27 19:18
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Service
public class LoginService implements LoginEbi {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisService redisService;

    @Transactional
    public User login(HttpServletRequest request, HttpServletResponse response, LoginVo loginVo){
        User user = userDao.getUserInfoByLoginVo(loginVo);
        if(user == null || user.getSalt() == null ||
                user.getPassword() == null || user.getId() == null){
            throw new GlobalException(CodeMsg.USERNAME_NOT_EXIST_ERROR_MSG);
        }
        if(user.getActivated() == User.NOT_ACTIVATED){
            throw new GlobalException(CodeMsg.USER_NOT_ACTIVATED_ERROR_MSG);
        }
        String httpPass = loginVo.getPassword();

        String finalPass = MD5Utils.httpPassToDbPass(httpPass, user.getSalt());
        if(!passwordCheck(user.getPassword(), finalPass)){
            throw new GlobalException(CodeMsg.INCORRECT_PASSWORD_ERROR_MSG);
        }
        updateLoginTime(user);

        // Set user login status to Session
        String userLoginSession = user.userLoginSession(user.getUsername(), user.getSalt());
        HttpSession session = request.getSession();
        session.setAttribute(User.USER_LOGIN, userLoginSession);

        // Set remember me logic
        if(loginVo.isRememberMe()){
            session.setAttribute(User.USER_REMEMBER_ME, User.USER_REMEMBER_ME_TOKEN);
        }else{
            session.removeAttribute(User.USER_REMEMBER_ME);
        }

        session.setMaxInactiveInterval(24 * 3600);

        // Create a distributed session for user if select remember me.
        String token = UUIDUtils.uuid();
        redisService.set(UserKey.userToken, token,  user);
        Cookie cookie = new Cookie(User.USER_TOKEN, token);
        cookie.setMaxAge(UserKey.userToken.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
        return user;
    }

    @Override
    public void logout(String token, HttpSession session, HttpServletRequest request) throws Exception {

        session.invalidate();
        redisService.delete(UserKey.userToken, token);
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            cookie.setMaxAge(0);
        }
    }

    private boolean updateLoginTime(User user){
        if(user == null || user.getId() == null){
            throw new GlobalException(CodeMsg.USERNAME_NOT_EXIST_ERROR_MSG);
        }
        Long currentTime = System.currentTimeMillis();
        userDao.updateLoginTime(user.getId(), currentTime);
        return true;
    }

    private static boolean passwordCheck(String dbPassword, String httpPassword){
        return dbPassword.equals(httpPassword);
    }
}
