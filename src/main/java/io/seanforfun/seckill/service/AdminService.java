package io.seanforfun.seckill.service;

import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.service.ebi.AdminEbi;
import io.seanforfun.seckill.service.ebi.UserEbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/1 17:09
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Service
public class AdminService extends UserService implements AdminEbi {
    @Autowired
    private UserEbi userService;

    @Override
    public User activateUser(Long id) {
        //1. Check if user exist.
        User user = userService.getUserById(id);
        if(user == null){
            return null;
        }
        //2. activate user.
        userService.updateUserActivateStatus(user, User.ACTIVATED);
        return user;
    }

    @Override
    public User rejectUser(Long id) {
        //1. Check if user exist.
        User user = userService.getUserById(id);
        if(user == null){
            return null;
        }
        //2. Reject user.
        userService.updateUserActivateStatus(user, User.REJECTED);
        return user;
    }

    @Override
    @Transactional
    public void setAdmin(Long id) {
        User user = userService.getUserById(id);
        userService.updateUserActivateStatus(user, User.ACTIVATED);
        userService.setAdminStatus(user, User.IS_ADMIN);
    }
}
