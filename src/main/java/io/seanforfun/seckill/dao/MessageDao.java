package io.seanforfun.seckill.dao;

import io.seanforfun.seckill.entity.domain.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    @Select("Select id, title, msg, sendTime, fromUser, senderName from Message where hasRead = #{hasRead} and toUser = #{toUser}")
    List<Message> getMessageByUserIdAndStatus(@Param("toUser") Long id, @Param("hasRead") boolean b);

    /**
     *  Update
     */

    /**
     *  Insert
     */
    @Insert("Insert into message (id, fromUser, toUser, title, " +
            "msg, hasRead, sendTime, senderName) values (#{id}, #{fromUser}, #{toUser}," +
            " #{title}, #{msg}, #{hasRead}, #{sendTime}, #{senderName})")
    void saveUnreadMessage(Message message);

    /**
     *  Delete
     */
}
