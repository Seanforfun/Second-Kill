package io.seanforfun.seckill.service.ebi;

import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.entity.vo.LoginVo;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/27 20:32
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public interface LoginEbi {

    /**
     * @param loginVo
     * @return true if login successfully, else false.
     * @throws Exception
     */
    User login(LoginVo loginVo) throws Exception;
}
