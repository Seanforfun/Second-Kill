package io.seanforfun.seckill.controller;

import io.seanforfun.seckill.entity.domain.Message;
import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.entity.domain.VehicleDetail;
import io.seanforfun.seckill.entity.vo.VehicleVo;
import io.seanforfun.seckill.service.ebi.VehicleEbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/4 13:49
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Controller
@RequestMapping("/vehicle")
@Configuration
@ConfigurationProperties
@PropertySource(value="classpath:/properties/pagination.properties")
public class VehicleController {

    @Autowired
    private VehicleEbi vehicleService;

    @Autowired
    private VehicleVo vehicleVo;

    @Value("${maxVehiclePerPage}")
    private Long maxVehiclePerPage;

    @RequestMapping(value = {"/list"})
    @ResponseBody
    public ModelAndView listVehicles(ModelAndView mv, User user, List<Message> messages){
        // Get Vehicles from db or redis.
        List<VehicleDetail> instockVehicles = vehicleService.getInstockVehicles();
        vehicleVo.setVehicles(instockVehicles);
        mv.addObject("vehicleVo", vehicleVo);
        // Redirect to listing page and show all vehicles.
        mv.setViewName("/pages/vehicle/vehicleList.html");
        return mv;
    }

    @RequestMapping("/sold")
    @ResponseBody
    public ModelAndView soldVehicles(ModelAndView mv, User user, List<Message> messages){
        List<VehicleDetail> soldVehicles = vehicleService.getSoldVehicles();
        vehicleVo.setVehicles(soldVehicles);
        mv.addObject("vehicleVo", vehicleVo);
        // Redirect to listing page and show all vehicles.
        mv.setViewName("/pages/vehicle/vehicleList.html");
        return mv;
    }

    @RequestMapping("/toAddPage")
    @ResponseBody
    public ModelAndView toAddPage(ModelAndView mv, User user, List<Message> messages){
        mv.setViewName("/pages/vehicle/addVehicle.html");
        return mv;
    }
}
