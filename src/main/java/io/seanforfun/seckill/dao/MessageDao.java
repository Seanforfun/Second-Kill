package io.seanforfun.seckill.dao;

import io.seanforfun.seckill.entity.domain.Message;
import org.apache.ibatis.annotations.*;
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
    @Select("Select id, title, msg, sendTime, fromUser, senderName from message where status = #{status} and toUser = #{toUser}")
    List<Message> getMessageByUserIdAndStatus(@Param("toUser") Long id, @Param("status") Integer status);

    @Select("Select id, title, msg, sendTime, fromUser, senderName from message where id = #{id}")
    Message getMessageById(@Param("id") Long messageId);

    /**
     *  Update
     */
    @Update("Update message set status = #{status} where id = #{messageId}")
    void setMessageStatusById(@Param("messageId") Long messageId, @Param("status") Integer status);

    /**
     *  Insert
     */
    @Insert("Insert into message (id, fromUser, toUser, title, " +
            "msg, status, sendTime, senderName) values (#{id}, #{fromUser}, #{toUser}," +
            " #{title}, #{msg}, #{status}, #{sendTime}, #{senderName})")
    void saveUnreadMessage(Message message);

    /**
     *  Delete
     */
}
