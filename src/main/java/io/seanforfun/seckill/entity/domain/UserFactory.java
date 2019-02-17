package io.seanforfun.seckill.entity.domain;

import io.seanforfun.seckill.utils.SnowFlakeUtils;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/29 9:54
 * @description: User factory to create prototype users.
 * @modified:
 * @version: 0.0.1
 */
public class UserFactory {

    public static final UserFactory USER_FACTORY = new UserFactory();

    private UserFactory(){}
    /**
     * @return Admin user.
     */
    public User produceAdmin(){
        User admin = new User();
        admin.setAdmin(User.IS_ADMIN);
        userAware(admin);
        return admin;
    }

    /**
     * @return Normal user.
     */
    public User produceUser(){
        User user = new User();
        user.setAdmin(User.NOT_ADMIN);
        user.setId(SnowFlakeUtils.getSnowFlakeId());
        userAware(user);
        return user;
    }

    public User produceEmptyUser(){
        User user = new User();
        userAware(user);
        return user;
    }

    /**
     * Setup parameters for users(Same for both admin and user)
     * @param user
     */
    private void userAware(User user){
        user.setActivated(User.NOT_ACTIVATED);
        user.setRegisterTime(System.currentTimeMillis());
        user.setLastModifiedTime(User.NOT_USED);
        user.setLastLoginTime(User.NOT_USED);
        user.setSalt(randomSalt());
    }

    private String randomSalt(){
        return RandomStringUtils.random(10, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }
}
