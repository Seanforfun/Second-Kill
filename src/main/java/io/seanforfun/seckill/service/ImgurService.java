package io.seanforfun.seckill.service;

import io.seanforfun.seckill.dao.ImageDao;
import io.seanforfun.seckill.entity.domain.Image;
import io.seanforfun.seckill.entity.enums.ImageSource;
import io.seanforfun.seckill.entity.enums.ImageType;
import io.seanforfun.seckill.exceptions.GlobalException;
import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.service.ebi.ImageEbi;
import io.seanforfun.seckill.utils.JsonUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

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
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("Authorization", "Client-ID " + clientId);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("image", new String(Base64.encodeBase64(multipartFile.getBytes())));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
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
    public Image deleteImage(Image image) throws Exception {
        if(image != null && image.getSource() == ImageSource.IMAGE_FROM_IMGUR){
            String deleteHash = image.getDeleteHash();
            deleteImageFromImgurByDeleteHash(deleteHash);
            imageDao.updateImageExistById(image.getId(), Image.IMAGE_NOT_EXIST);
        }
        return image;
    }

    /**
     * Use delete hash to delete image on Imgur.
     * curl --location --request DELETE "https://api.imgur.com/3/image/{{imageDeleteHash}}" \
     *   --header "Authorization: Client-ID {{clientId}}"
     * @param deleteHash
     */
    private void deleteImageFromImgurByDeleteHash(String deleteHash){
        if(deleteHash == null || deleteHash.length() == 0){
            throw new GlobalException(CodeMsg.IMGUR_DELETE_IMAGE_ERROR_MSG);
        }
        // Delete image from imgur by using deleteHash.
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Client-ID " + clientId);
        HttpEntity<Object> request = new HttpEntity<>(headers);
        ResponseEntity<String> deleteResponse = restTemplate.exchange("https://api.imgur.com/3/image/" + deleteHash, HttpMethod.DELETE, request, String.class);
        if(deleteResponse.getStatusCode() != HttpStatus.OK){
            throw  new GlobalException(CodeMsg.IMGUR_DELETE_IMAGE_ERROR_MSG);
        }
        String json = deleteResponse.getBody();
        Boolean success = JsonUtils.get(json, "success", Boolean.class);
        if(!success){
            throw  new GlobalException(CodeMsg.IMGUR_DELETE_IMAGE_ERROR_MSG);
        }
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
