package io.seanforfun.seckill.service;

import io.seanforfun.seckill.dao.UserDao;
import io.seanforfun.seckill.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
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

    public boolean tx() {
        User user1 = new User();
        user1.setId(2L);
        user1.setName("222");
        userDao.insert(user1);

        User user2 = new User();
        user2.setId(1L);
        user2.setName("1111");
        userDao.insert(user2);
        return true;
    }
}
