package io.seanforfun.seckill.service;

import io.seanforfun.seckill.dao.MessageDao;
import io.seanforfun.seckill.entity.domain.Message;
import io.seanforfun.seckill.service.ebi.MessageEbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/5 12:33
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Service
public class MessageService implements MessageEbi {

    @Autowired
    private MessageDao messageDao;

    @Override
    @Transactional
    public void sendMessageToUser(Message message) {
        messageDao.saveUnreadMessage(message);
    }
}
