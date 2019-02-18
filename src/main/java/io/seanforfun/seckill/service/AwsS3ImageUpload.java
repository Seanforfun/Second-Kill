package io.seanforfun.seckill.service;

import io.seanforfun.seckill.entity.domain.Image;
import io.seanforfun.seckill.entity.enums.ImageType;
import io.seanforfun.seckill.service.ebi.ImageEbi;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/18 17:09
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public class AwsS3ImageUpload extends AbstractImageService implements ImageEbi<MultipartFile, Image> {
    @Override
    public Image uploadImage(MultipartFile file, ImageType imageType, Long associateId) throws Exception {
        return null;
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
    public Image deleteImage(Image image) throws Exception {
        return null;
    }

    @Override
    public void deleteImages(Collection<Image> images) throws Exception {

    }
}
