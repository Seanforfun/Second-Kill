package io.seanforfun.seckill.service;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import io.seanforfun.seckill.dao.UserDao;
import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.entity.vo.UserVo;
import io.seanforfun.seckill.redis.RedisService;
import io.seanforfun.seckill.redis.UserKey;
import io.seanforfun.seckill.service.ebi.UserEbi;
import io.seanforfun.seckill.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/31 10:19
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Service
public class UserService implements UserEbi {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Long id) {
        User user = null;
        if(id == null){
            return user;
        }
        user = redisGetUserById(id);
        if(user != null) {
            return user;
        }
        user = dbGetUserById(id);
        redisService.set(UserKey.getKeyForId, "" + id, user);
        return user;
    }

    @Override
    public User getUserByToken(HttpServletResponse response, String token) throws Exception {
        User user = null;
        if(!StringUtils.isEmpty(token)){
            user = redisService.get(UserKey.userToken, token, User.class);
            updateUserSession(token, user, response);
        }
        return user;
    }

    @Override
    public boolean exists(User user) {
        return userDao.getUserIdByUsername(user) != null;
    }

    @Override
    public boolean checkLogout(User user, HttpServletRequest request, HttpSession session) {
        String loginToken = (String )session.getAttribute(User.USER_LOGIN);
        if(!StringUtils.isEmpty(loginToken)){
            return false;
        }
        String cookieValue = CookieUtils.getValueFromCookie(request.getCookies(), User.USER_TOKEN);
        return StringUtils.isEmpty(cookieValue);
    }

    @Override
    @Transactional
    public void updateUserPassword(User user) {
        userDao.updateUserPasswordAndSalt(user);
    }

    @Override
    public int getUnapprovedUserNumber() {
        return userDao.getUserNumberByUserStatus(User.NOT_ACTIVATED);
    }

    @Override
    public List<User> getUnapprovedUserListByPage(UserVo userVo) {
        Long numPerPage = userVo.getNumPerPage();
        Long currentPageNum = userVo.getCurrentPageNum();
        Long currentPageIndex = (currentPageNum - 1) * numPerPage;
        List<User> users = userDao.getUserListByUserStatus(User.NOT_ACTIVATED, currentPageIndex, userVo.getNumPerPage());
        return users;
    }

    @Override
    public List<User> getUnapprovedUserList(UserVo userVo) {
        return userDao.getAllInactiveUserByStatus(User.NOT_ACTIVATED);
    }

    @Override
    @Transactional
    public void updateUserActivateStatus(User user, int newStatus) {
        user.setActivated(newStatus);
        //Step 1 : Update database status;
        userDao.updateUserActivateStatus(user.getId(), newStatus);
        // Step 2: Update redis by id;
        redisService.set(UserKey.getKeyForId, "" + user.getId(), user);
    }

    @Override
    @Transactional
    public void setAdminStatus(User user, boolean adminStatus) {
        user.setAdmin(adminStatus);
        //Step 1 : Update database status;
        userDao.updateUserAdminStatus(user.getId(), adminStatus);
        // Step 2: Update redis by id;
        redisService.set(UserKey.getKeyForId, "" + user.getId(), user);
    }

    @Override
    public List<User> getActivatedUserList() {
        List<User> userList = null;
        if(userList != null){
            userList = redisGetUserList();
            return userList;
        }
        userList = userDao.getUserListByStatus(User.ACTIVATED);
        redisService.set(UserKey.activateUserListToken, "", userList);
        return userList;
    }

    protected void updateUserSession(String token, User user, HttpServletResponse response){
        redisService.set(UserKey.userToken, token,  user);
        Cookie cookie = new Cookie(User.USER_TOKEN, token);
        cookie.setMaxAge(UserKey.userToken.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * Redis methods.
     */
    private User redisGetUserById(Long id){
        User user = redisService.get(UserKey.getKeyForId, "" + id, User.class);
        return user;
    }

    @Override
    public boolean userInRedisSession(String token) {
        return redisService.exists(UserKey.userToken, token);
    }

    private List<User> redisGetUserList(){
        List<User> userlist = redisService.get(UserKey.activateUserListToken, "", List.class);
        return userlist;
    }

    /**
     * DB methods
     */
    private User dbGetUserById(Long id){
        return userDao.getById(id);
    }
}
