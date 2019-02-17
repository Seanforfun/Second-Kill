package io.seanforfun.seckill.service.ebi;

import io.seanforfun.seckill.entity.domain.User;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/30 10:25
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public interface AdminEbi {
    /**
     * Activate user with user id.
     * @param id
     * @return
     */
    User activateUser(Long id);

    /**
     * Reject current user.
     * @param id
     * @return
     */
    User rejectUser(Long id);

    /**
     * Set a user to admin
     * @param id
     */
    void setAdmin(Long id);
}
