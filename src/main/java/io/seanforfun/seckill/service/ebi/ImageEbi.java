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
     * Upload image to a third party server or local file system.
     * Synchronous operation.
     * @param t
     * @param imageType
     * @param associateId
     * @return
     * @throws Exception
     */
    R uploadImage(T t, ImageType imageType, Long associateId) throws Exception;

    /**
     * Upload image to a third party server or local file system.
     * Asynchronous operation.
     * @param t
     * @param imageType
     * @param associateId
     * @return
     * @throws Exception
     */
    R uploadImageAsync(T t, ImageType imageType, Long associateId) throws Exception;

    /**
     * Upload a set of images.
     * @param images
     * @param imageType Vehicle or User
     * @param associateId Associate object id
     * @return List of returned images.
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
     * Get image list by using vehicle id.
     * @return
     * @throws Exception
     */
    List<R> getImageListByVehicleId(Long vehicleId) throws Exception;

    /**
     * Get the base64 string of a image file, could be from Local file system, imgur or aws s3.
     * I write this method for getting the bytes and cache them in redis so I can reduce the
     * request number and improve efficiency.
     * @param image
     * @return
     */
    String getBase64String(Image image) throws Exception;

    /**
     * Set the image base64 string for all images in the list.
     * @param images
     * @return
     * @throws Exception
     */
    List<Image> getImageBase64StringForImages(List<Image> images) throws Exception;

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

    /**
     * Check if given image exists.
     * @param image
     * @return boolean, if image exists.
     * @throws Exception
     */
    boolean imageExists(Image image) throws Exception;
}
