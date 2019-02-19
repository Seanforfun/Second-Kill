package io.seanforfun.seckill.service.ebi;

import io.seanforfun.seckill.entity.domain.Image;
import io.seanforfun.seckill.entity.enums.ImageType;

import java.util.Collection;
import java.util.List;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/13 13:30
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public interface ImageEbi<T, R> {

    /**
     * Upload image to a third party server.
     * @param t
     * @param imageType
     * @param associateId
     * @return
     * @throws Exception
     */
    R uploadImage(T t, ImageType imageType, Long associateId) throws Exception;

    /**
     * Upload a set of images.
     * @param images
     * @return
     */
    List<R> uploadImages(Collection<T> images, ImageType imageType, Long associateId) throws Exception;

    /**
     * Get Image from id.
     * @param id
     * @return
     * @throws Exception
     */
    R getImage(Long id) throws Exception;


    /**
     * Delete a images in third party server.
     * @param image
     * @return
     */
    R deleteImage(Image image) throws Exception;

    /**
     * Delete a set of images by Id.
     * @param images
     * @return
     */
    void deleteImages(Collection<Image> images) throws Exception;
}
