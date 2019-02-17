package io.seanforfun.seckill.service.ebi;

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
     * Get Image from link.
     * @param link
     * @return
     * @throws Exception
     */
    R getImage(String link) throws Exception;

    /**
     * Delete a images in third party server.
     * @param id
     * @return
     */
    R deleteImage(Long id) throws Exception;

    /**
     * Delete saved image according to linl
     * @param link
     * @return
     * @throws Exception
     */
    R deleteImageByLink(String link) throws Exception;

    /**
     * Delete a set of images by Id.
     * @param imageIds
     * @return
     */
    R deleteImages(Collection<Long> imageIds) throws Exception;

    /**
     * Delete all saveImages by link
     * @param links
     * @return
     * @throws Exception
     */
    R deleteImagesByLink(Collection<String> links) throws Exception;
}
