package io.seanforfun.seckill.controller;

import io.seanforfun.seckill.config.EnvironmentConfigBean;
import io.seanforfun.seckill.entity.domain.Image;
import io.seanforfun.seckill.entity.domain.Message;
import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.entity.domain.VehicleDetail;
import io.seanforfun.seckill.entity.vo.VehicleInfoVo;
import io.seanforfun.seckill.entity.vo.VehicleVo;
import io.seanforfun.seckill.exceptions.GlobalException;
import io.seanforfun.seckill.redis.PageKey;
import io.seanforfun.seckill.redis.RedisService;
import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.result.Result;
import io.seanforfun.seckill.service.ebi.VehicleEbi;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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

    @Autowired
    private EnvironmentConfigBean environmentConfigBean;

    @Autowired
    private ObjectFactory<VehicleInfoVo> vehicleInfoVoFactory;


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
        if(environmentConfigBean.getCachePage()) {
            html = redisService.get(PageKey.getPageByName, "toAddPage", String.class);
            if (html != null) {
                return html;
            }
        }
        IWebContext ctx =new WebContext(request,response,
                request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("pages/vehicle/addVehicle", ctx);
        redisService.set(PageKey.getPageByName, "toAddPage", html);
        return html;
    }

    @RequestMapping(value = "/toMultipleAddPage", produces = "text/html")
    public String toMultipleAddPage(User user, List<Message> messages,
                                    HttpServletRequest request, HttpServletResponse response,
                                    Model model){
        String html = null;
        if(environmentConfigBean.getCachePage()) {
            html = redisService.get(PageKey.getPageByName, "toMultipleAddPage", String.class);
            if(html != null){
                return html;
            }
        }
        IWebContext ctx =new WebContext(request,response,
                request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("pages/vehicle/addVehicles", ctx);
        redisService.set(PageKey.getPageByName, "toMultipleAddPage", html);
        return html;
    }

    @RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> addVehicle(User user, @Valid VehicleDetail vehicleDetail, MultipartHttpServletRequest request ) throws Exception {
        // Get all uploaded files.
        Map<String, MultipartFile> fileMap = request.getFileMap();
        Set<Map.Entry<String, MultipartFile>> entries = fileMap.entrySet();
        for(Map.Entry<String, MultipartFile> file : entries){
            MultipartFile image = file.getValue();
            String contentType = image.getContentType();
            // Check type of each file.
            if(contentType == null || !contentType.contains("image")){
                throw new GlobalException(CodeMsg.FILE_NOT_IMAGE_MSG);
            }
        }

        // Save vehicle to db.
        vehicleService.saveVehicle(vehicleDetail, user.getId(), fileMap);
        return Result.success(true);
    }

    @RequestMapping("/info/{id}")
    @ResponseBody
    public Result<VehicleInfoVo> vehicleInfo(User user, List<Message> messages, @PathVariable("id") Long id) throws Exception {
        //Step 1: Create an empty vehicleVo object.
        VehicleInfoVo vehicleInfoVo = vehicleInfoVoFactory.getObject();
        //Step 2: Insert user related information.
        vehicleInfoVo.setUser(user);
        vehicleInfoVo.setMessages(messages);
        //Step 3.1: Set vehicle related data.
        //Step 3.2: Get vehicle detail.
        VehicleDetail vehicleDetail = vehicleService.getVehicleInfoById(id);
        vehicleInfoVo.setVehicleDetail(vehicleDetail);
        //Step 3.3: Get Images.
        List<Image> vehicleImages = vehicleService.getVehicleImagesById(id);
        vehicleInfoVo.setVehicleImages(vehicleImages);
        //Step 3.4: Get Qr code for vehicle
        byte[] qrCode = vehicleService.getQrCodeById(id);
        vehicleInfoVo.setBase64QRString(Base64.encodeBase64String(qrCode));
        return Result.success(vehicleInfoVo);
    }
}
