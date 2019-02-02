package io.seanforfun.seckill.controller;

import io.seanforfun.seckill.config.ConfigBean;
import io.seanforfun.seckill.config.annotations.Access;
import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.entity.vo.UserVo;
import io.seanforfun.seckill.service.AdminService;
import io.seanforfun.seckill.service.ebi.AdminEbi;
import io.seanforfun.seckill.service.ebi.UserEbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/1 13:27
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Controller
@RequestMapping("/admin")
@Access(authority = "ADMIN")
public class AdminController {

    @Autowired
    private UserVo userVo;

    @Autowired
    private UserEbi userService;

    @Autowired
    private AdminEbi AdminService;

    @RequestMapping(value = {"/userApproval"})
    @ResponseBody
    public ModelAndView adminToUserManagement(ModelAndView mv, User user){
        List<User> unapprovedUserList = userService.getUnapprovedUserList(userVo);
        userVo.setUser(unapprovedUserList);
        mv.addObject("userVo", userVo);
        mv.addObject("user", user);
        mv.setViewName("/pages/approveUser.html");
        return mv;
    }

    @RequestMapping("/approve/{id}")
    @ResponseBody
    public ModelAndView adminApprove(@PathVariable(name = "id", required = true) Long id, ModelAndView mv){
        AdminService.activateUser(id);
        mv.setViewName("redirect:/admin/userApproval");
        return mv;
    }

    @RequestMapping("/reject/{id}")
    @ResponseBody
    public ModelAndView adminReject(@PathVariable(name = "id", required = true) Long id, ModelAndView mv){
        AdminService.rejectUser(id);
        mv.setViewName("redirect:/admin/userApproval");
        return mv;
    }

    @RequestMapping("/info/{id}")
    @ResponseBody
    public ModelAndView adminUserInfo(@PathVariable(name = "id", required = true) Long id, ModelAndView mv, User user){
        User detailUser = userService.getUserById(id);
        mv.addObject("userInstance", detailUser);
        mv.addObject("user", user);
        mv.setViewName("/pages/userInfo.html");
        return mv;
    }

    @RequestMapping("/admin/{id}")
    @ResponseBody
    public ModelAndView adminSetAdmin(@PathVariable(name = "id", required = true) Long id, ModelAndView mv, User user){
        AdminService.setAdmin(id);
        mv.setViewName("redirect:/admin/userApproval");
        return mv;
    }
}
