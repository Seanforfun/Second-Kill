package io.seanforfun.seckill.service;

import io.seanforfun.seckill.dao.UserDao;
import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.entity.vo.RegisterVo;
import io.seanforfun.seckill.exceptions.GlobalException;
import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.service.ebi.RegisterEbi;
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

    @Override
    @Transactional
    public boolean registerUser(RegisterVo registerVo) {
        String username = registerVo.getUsername();
        if(userDao.getUserNumByUsername(username) > 0){
            throw new GlobalException(CodeMsg.EXISTING_USERNAME_ERROR_MSG);
        }
        User registerUser = new User();
        registerUser.setUsername(username);
        registerUser.setActivated(User.NOT_ACTIVATED);
        registerUser.setAdmin(User.NOT_ADMIN);
        registerUser.setCountry(registerVo.getCountry());
        registerUser.setState(registerVo.getState());
        registerUser.setZip(registerVo.getZip());
        ZipValidator validator = ZipValidator.ZIP_VALIDATOR_MAP.get(registerVo.getCountry());
        if(!validator.validate(registerVo.getZip())){
            throw new GlobalException(CodeMsg.INCORRECT_ZIP_ERROR_MSG);
        }
        registerUser.setEmail(registerVo.getEmail());
        String randomSalt = RandomStringUtils.random(10, "ABCDEFGHIJKLMN");
        String dbPassword = MD5Utils.httpPassToDbPass(registerVo.getPassword(), randomSalt);
        registerUser.setSalt(randomSalt);
        registerUser.setPassword(dbPassword);
        registerUser.setLastLoginTime(0L);
        registerUser.setLastModifiedTime(0L);
        registerUser.setRegisterTime(System.currentTimeMillis());
        System.out.println(registerUser.toString());
        userDao.saveRegisterUser(registerUser);
        return true;
    }
}
