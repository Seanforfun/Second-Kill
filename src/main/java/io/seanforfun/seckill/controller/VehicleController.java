package io.seanforfun.seckill.controller;

import com.sun.webkit.PageCache;
import io.seanforfun.seckill.entity.domain.Message;
import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.entity.domain.VehicleDetail;
import io.seanforfun.seckill.entity.vo.VehicleVo;
import io.seanforfun.seckill.redis.PageKey;
import io.seanforfun.seckill.redis.RedisService;
import io.seanforfun.seckill.result.Result;
import io.seanforfun.seckill.service.ebi.VehicleEbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.context.webflux.SpringWebFluxContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = {"/list"})
    public String listVehicles(Model model, User user, List<Message> messages){
        // Get Vehicles from db or redis.
        List<VehicleDetail> instockVehicles = vehicleService.getInstockVehicles();
        vehicleVo.setVehicles(instockVehicles);
        model.addAttribute("vehicleVo", vehicleVo);
        // Redirect to listing page and show all vehicles.
        return "pages/vehicle/vehicleList";
    }

    @RequestMapping("/sold")
    public String soldVehicles(Model model, User user, List<Message> messages){
        List<VehicleDetail> soldVehicles = vehicleService.getSoldVehicles();
        vehicleVo.setVehicles(soldVehicles);
        model.addAttribute("vehicleVo", vehicleVo);
        // Redirect to listing page and show all vehicles.
        return "pages/vehicle/vehicleList";
    }

    @RequestMapping(value = "/toAddPage", produces="text/html")
    @ResponseBody
    public String toAddPage(User user, List<Message> messages, Model model, HttpServletRequest request, HttpServletResponse response){
        String html = null;
        // Redis get cached html page.
//        html = redisService.get(PageKey.getPageByName, "toAddPage", String.class);
//        if(html != null){
//            return html;
//        }
        IWebContext ctx =new WebContext(request,response,
                request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("pages/vehicle/addVehicle", ctx);
        redisService.set(PageKey.getPageByName, "toAddPage", html);
        return html;
    }

    @RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> addVehicle(@Valid VehicleDetail vehicleDetail, MultipartHttpServletRequest request ){
        System.out.println(vehicleDetail.toString());
        // Get all uploaded files.
        Map<String, MultipartFile> fileMap = request.getFileMap();
        Set<Map.Entry<String, MultipartFile>> entries = fileMap.entrySet();
        for(Map.Entry<String, MultipartFile> entry: entries){
            System.out.println(entry.getKey());
        }

        // Save vehicle to db.
        return Result.success(true);
    }
}
