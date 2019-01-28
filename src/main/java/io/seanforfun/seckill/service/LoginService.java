package io.seanforfun.seckill.service;

import io.seanforfun.seckill.dao.UserDao;
import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.entity.vo.LoginVo;
import io.seanforfun.seckill.exceptions.GlobalException;
import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.service.ebi.LoginEbi;
import io.seanforfun.seckill.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public boolean login(LoginVo loginVo){
        User userWithPassAndSalt = userDao.getUserInfoByLoginVo(loginVo);
        if(userWithPassAndSalt == null || userWithPassAndSalt.getSalt() == null ||
                userWithPassAndSalt.getPassword() == null || userWithPassAndSalt.getId() == null){
            throw new GlobalException(CodeMsg.USERNAME_NOT_EXIST_ERROR_MSG);
        }
        String httpPass = loginVo.getPassword();

        String finalPass = MD5Utils.httpPassToDbPass(httpPass, userWithPassAndSalt.getSalt());
        if(!passwordCheck(userWithPassAndSalt.getPassword(), finalPass)){
            throw new GlobalException(CodeMsg.INCORRECT_PASSWORD_ERROR_MSG);
        }
        updateLoginTime(userWithPassAndSalt);
        // TODO: Need to save the user information into session.
        return true;
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
