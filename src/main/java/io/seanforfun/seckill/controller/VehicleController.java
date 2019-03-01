package io.seanforfun.seckill.controller;

import io.seanforfun.seckill.config.EnvironmentConfigBean;
import io.seanforfun.seckill.entity.domain.Image;
import io.seanforfun.seckill.entity.domain.Message;
import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.entity.domain.VehicleDetail;
import io.seanforfun.seckill.entity.enums.ImageSource;
import io.seanforfun.seckill.entity.enums.ImageType;
import io.seanforfun.seckill.entity.vo.VehicleInfoVo;
import io.seanforfun.seckill.entity.vo.VehicleVo;
import io.seanforfun.seckill.exceptions.GlobalException;
import io.seanforfun.seckill.redis.PageKey;
import io.seanforfun.seckill.redis.RedisService;
import io.seanforfun.seckill.redis.VehicleKey;
import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.result.Result;
import io.seanforfun.seckill.service.LocalImageService;
import io.seanforfun.seckill.service.ebi.ImageEbi;
import io.seanforfun.seckill.service.ebi.VehicleEbi;
import io.seanforfun.seckill.utils.SnowFlakeUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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
@PropertySource(value= {"classpath:/properties/pagination.properties", "classpath:/properties/image.properties"})
public class VehicleController {

    @Autowired
    private VehicleEbi vehicleService;

    @Autowired
    @Qualifier("localImageService")
    private ImageEbi imageService;

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

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${image.source}")
    private String source;

    public static final float UPLOAD_VEHICLE_FAILED = -1.00F;

    public static final ExecutorService SAVE_VEHICLE_THREAD_POOL = Executors.newCachedThreadPool();

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

    @RequestMapping("/toVehicleInfo/{id}")
    public String toVehicleInfo(User user, List<Message> messages,
                                @PathVariable("id") Long id, HttpServletRequest request,
                                Model model) throws Exception {
        String html = null;
        html = redisService.get(PageKey.getPageByName, "/toVehicleInfo/" + id, String.class);
        if(html != null){
            return html;
        }
        VehicleInfoVo vehicleInfoVo = getVehicleInfoVo(user, messages, id);
        model.addAttribute("vehicleInfoVo", vehicleInfoVo);

//        redisService.get(, , )
        return "pages/vehicle/vehicleInfo";
    }

    @RequestMapping(value = "/uploadPercentage/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result<Float> checkUploadById(@PathVariable("id") Long id){
        String uploadPercentage = redisService.get(VehicleKey.getVehicleUploadPercentageById, "" + id, String.class);
        if(uploadPercentage == null){
            return Result.success(0F);
        }
        Float aFloat = Float.parseFloat(uploadPercentage);
        return Result.success(aFloat);
    }

    @RequestMapping("/mobile/info/{id}")
    @ResponseBody
    public Result<VehicleInfoVo> vehicleInfo(User user, List<Message> messages, @PathVariable("id") Long id) throws Exception {
        VehicleInfoVo vehicleInfoVo = getVehicleInfoVo(user, messages, id);
        return Result.success(vehicleInfoVo);
    }

    private VehicleInfoVo getVehicleInfoVo(User user, List<Message> messages, Long id) throws Exception {
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
        String qrCode = vehicleService.getBase64QrCodeById(id);
        vehicleInfoVo.setBase64QRString(qrCode);
        return vehicleInfoVo;
    }

    /**
     * Async Method
     */
    /**
     * Upload images and corresponding images.
     * This is an async method, we will check the parameters and then send the parameters to message queue and return waiting.
     * Front side will recursively call io.seanforfun.seckill.controller.VehicleController#checkUploadById(java.lang.Long) for getting
     * uploading percentage.
     * @param user
     * @param vehicleDetail
     * @param request
     * @return Return the id of the vehicle.
     * @throws Exception
     */
    @RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> addVehicle(User user, @Valid VehicleDetail vehicleDetail, MultipartHttpServletRequest request ) {
//        if(!vehicleService.vehicleCheckVin(vehicleDetail.getVin())){
//            throw new GlobalException(CodeMsg.DUPLICATE_VEHICLE_VIN_MSG);
//        }
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
        ImageSource imageSource = imageService.getImageSource(source);
        vehicleDetail.setId(SnowFlakeUtils.getSnowFlakeId());
        List<Image> imageList = fileMap.values().parallelStream().map((mFile) -> {
            Image image = null;
            try {
                image = imageService.getInitializedImage(mFile.getName(), imageSource, mFile.getBytes(), ImageType.VEHICLE_IMAGE, vehicleDetail.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return image;
        }).collect(Collectors.toList());
        // Save vehicle using another thread.
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
//                throw new RuntimeException();
                vehicleService.saveVehicle(vehicleDetail, user.getId(), imageList);
            } catch (Exception e) {
                e.printStackTrace();
                redisService.set(VehicleKey.getVehicleUploadPercentageById, vehicleDetail.getId() + "", UPLOAD_VEHICLE_FAILED);
                throw new GlobalException(CodeMsg.VEHICLE_ADD_ERROR_MSG);
            }
        }, SAVE_VEHICLE_THREAD_POOL);
        future.handle((r, e) -> {
            if (e != null) {
                e.printStackTrace();
                redisService.set(VehicleKey.getVehicleUploadPercentageById, vehicleDetail.getId() + "", UPLOAD_VEHICLE_FAILED);
                throw new GlobalException(CodeMsg.VEHICLE_ADD_ERROR_MSG);
            }
            return r;
        });
        return Result.success(vehicleDetail.getId() + "");
    }
}
