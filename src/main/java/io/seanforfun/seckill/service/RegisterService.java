package io.seanforfun.seckill.service;

import io.seanforfun.seckill.dao.UserDao;
import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.entity.vo.RegisterVo;
import io.seanforfun.seckill.exceptions.GlobalException;
import io.seanforfun.seckill.redis.RedisService;
import io.seanforfun.seckill.redis.UserKey;
import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.service.ebi.RegisterEbi;
import io.seanforfun.seckill.service.ebi.UserEbi;
import io.seanforfun.seckill.utils.MD5Utils;
import io.seanforfun.seckill.validator.ZipValidator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/28 15:40
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Service
public class RegisterService implements RegisterEbi {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserEbi userService;
    @Autowired
    private RedisService redisService;

    @Override
    @Transactional
    public boolean registerUser(User user) {
        ZipValidator validator = ZipValidator.ZIP_VALIDATOR_MAP.get(user.getCountry());
        if(!validator.validate(user.getZip())){
            throw new GlobalException(CodeMsg.INCORRECT_ZIP_ERROR_MSG);
        }
        //check if current user is the init admin user.
        if(!userService.hasAdmin()){
            user.setAdmin(true);
            user.setActivated(User.ACTIVATED);
        }
        Long result = userDao.saveRegisterUser(user);
        if(result <= 0){
            throw new GlobalException(CodeMsg.USER_REGISTER_FAILED_MSG);
        }else{
            redisService.inc(UserKey.userNumByAdmin, "true");
        }
        return true;
    }

    @Override
    public boolean validUsername(String username){
        return userDao.getUserNumByUsername(username) == 0;
    }
}
