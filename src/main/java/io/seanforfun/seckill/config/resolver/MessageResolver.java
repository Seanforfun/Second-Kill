package io.seanforfun.seckill.config.resolver;

import io.seanforfun.seckill.entity.domain.Message;
import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.service.ebi.MessageEbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/5 15:38
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Service
public class MessageResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private MessageEbi messageEbi;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(List.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        User user = (User)mavContainer.getModel().get("user");
        if(user == null){
            return null;
        }
        List<Message> messages = messageEbi.getUserUnreadMsg(user);
        mavContainer.addAttribute("unreadMsg", messages);
        return messages;
    }
}
