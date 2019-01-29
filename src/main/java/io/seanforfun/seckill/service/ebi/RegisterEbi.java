package io.seanforfun.seckill.service.ebi;

import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.entity.vo.RegisterVo;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/28 15:38
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public interface RegisterEbi {
    /**
     * Add register user into database, at this stage, user is saved but not activated, the user still
     * cannot login.
     * User can login until activated by administrator.
     * @param user
     * @return
     */
    boolean registerUser(User user);


    /**
     * Check if current username is valid, including checking duplication etc.
     * @param username
     * @return true if valid, else false.
     */
    boolean validUsername(String username);
}
