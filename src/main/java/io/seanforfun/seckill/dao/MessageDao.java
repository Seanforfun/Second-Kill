package io.seanforfun.seckill.dao;

import io.seanforfun.seckill.entity.domain.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/5 12:36
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Mapper
@Repository
public interface MessageDao {
    /**
     *  Read
     */


    /**
     *  Update
     */

    /**
     *  Insert
     */
    @Insert("Insert into message (id, fromUser, toUser, title, " +
            "msg, hasRead, sendTime) values (#{id}, #{fromUser}, #{toUser}," +
            " #{title}, #{msg}, #{hasRead}, #{sendTime})")
    void saveUnreadMessage(Message message);

    /**
     *  Delete
     */
}
