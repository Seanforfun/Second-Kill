package io.seanforfun.seckill.service;

import io.seanforfun.seckill.dao.UserDao;
import io.seanforfun.seckill.entity.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/24 12:46
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Service
@Transactional
public class UserService {
    @Autowired
    private UserDao userDao;

    public User getUserById(long id){
        return userDao.getById(id);
    }

}
