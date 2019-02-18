package io.seanforfun.seckill.service;

import io.seanforfun.seckill.dao.ImageDao;
import io.seanforfun.seckill.entity.domain.Image;
import io.seanforfun.seckill.entity.enums.ImageSource;
import io.seanforfun.seckill.entity.enums.ImageType;
import io.seanforfun.seckill.exceptions.GlobalException;
import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.service.ebi.ImageEbi;
import io.seanforfun.seckill.utils.JsonUtils;
import io.seanforfun.seckill.utils.SnowFlakeUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@Service
@Configuration
@PropertySource(value = "classpath:/properties/image.properties")
public class ImgurService extends AbstractImageService implements ImageEbi<MultipartFile, Image> {

    @Value("${image.imgur.clientId}")
    private String clientId;

    @Value("${image.imgur.clientSecret}")
    private String clientSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ImageDao imageDao;

    @Override
    public Image uploadImage(MultipartFile multipartFile, ImageType imageType, Long associateId) throws Exception {
        // Set initial Image information.
        String name = multipartFile.getName();
        byte[] imageBytes = multipartFile.getBytes();
        Image emptyImage = getInitializedImage(name, ImageSource.IMAGE_FROM_IMGUR,
                imageBytes, imageType, associateId);
        // Upload Image to imgur.
        // TODO Need to finish this part.
        /**
         * request:
         * curl --location --request POST "https://api.imgur.com/3/image" \
         *   --header "Authorization: Client-ID {{clientId}}" \
         *   --form "image=R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7"
         * response:
         *
         {
         "data": {
         "id": "orunSTu",
         "title": null,
         "description": null,
         "datetime": 1495556889,
         "type": "image/gif",
         "animated": false,
         "width": 1,
         "height": 1,
         "size": 42,
         "views": 0,
         "bandwidth": 0,
         "vote": null,
         "favorite": false,
         "nsfw": null,
         "section": null,
         "account_url": null,
         "account_id": 0,
         "is_ad": false,
         "in_most_viral": false,
         "tags": [],
         "ad_type": 0,
         "ad_url": "",
         "in_gallery": false,
         "deletehash": "x70po4w7BVvSUzZ",
         "name": "",
         "link": "http://i.imgur.com/orunSTu.gif"
         },
         "success": true,
         "status": 200
         }
         */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization: Client-ID", clientId);
        MultiValueMap<String, Byte[]> map= new LinkedMultiValueMap<>();
        map.add("image", ArrayUtils.toObject(Base64.encodeBase64(multipartFile.getBytes())));
        HttpEntity<MultiValueMap<String, Byte[]>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://api.imgur.com/3/image", request, String.class);
        if(responseEntity.getStatusCode() != HttpStatus.OK){
            throw new GlobalException(CodeMsg.IMGUR_UPLOAD_IMAGE_ERROR_MSG);
        }else{
            String responseJson = responseEntity.getBody();
            Boolean success = JsonUtils.get(responseJson, "success", Boolean.class);
            if(!success){
                throw new GlobalException(CodeMsg.IMGUR_UPLOAD_IMAGE_ERROR_MSG);
            }
            String link = JsonUtils.get(responseJson, "data.link", String.class);
            String deleteHash = JsonUtils.get(responseJson, "data.deletehash", String.class);
            emptyImage.setLink(link);
            emptyImage.setDeleteHash(deleteHash);
        }
        imageDao.saveImageInfo(emptyImage);
        return emptyImage;
    }

    @Override
    public List<Image> uploadImages(Collection<MultipartFile> images, ImageType imageType, Long associateId) throws Exception {
        return null;
    }

    @Override
    public Image getImage(String link) throws Exception {
        return null;
    }

    @Override
    public Image deleteImage(Long id) throws Exception {
        return null;
    }

    @Override
    public Image deleteImage(String link) throws Exception {
        return null;
    }

    @Override
    public Image deleteImages(Collection<Long> imageIds) throws Exception {
        return null;
    }

    @Override
    public Image deleteImagesByLink(Collection<String> links) throws Exception {
        return null;
    }

    @Bean
    public RestTemplate postTemplate(){
        return new RestTemplate();
    }
}
