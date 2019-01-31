package io.seanforfun.seckill.config;

import io.seanforfun.seckill.config.Interceptor.LoginInterceptor;
import io.seanforfun.seckill.entity.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/30 21:24
 * @description: ${description}
 * @modified:
 * @version: 0.01
 */
@Configuration
public class CustomConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/user/tologin");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE );
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/manage/**");
    }
}
