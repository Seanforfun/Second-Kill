package io.seanforfun.seckill.dao;

import io.seanforfun.seckill.entity.domain.Image;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/15 15:04
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Mapper
@Repository
public interface ImageDao {

    // Save images
    @Insert("Insert into Image (id, link, name, source, type, associateId, deleteHash, imageHash) " +
            "values (#{id}, #{link}, #{name}, #{source}, #{type}, #{associateId}, #{deleteHash}, #{imageHash})")
    void saveImageInfo(Image image);

    // Load image information.
    @Select("Select id, link, name, source, type, associateId, deleteHash from Image where id = #{id}")
    Image getImageById(@Param("id") Long id);

    @Select("Select id, link, name, source, type, associateId, deleteHash, exists from Image where associateId = #{vehicleId} ")
    List<Image> getImagesByVehicleId(@Param("vehicleId") Long vehicleId);

    // Update image information.
    @Update("Update Image set exist = #{exist} where id = #{id}")
    void updateImageExistById(@Param("id") Long id, @Param("exist") Integer exist);

    // Delete image information.
    @Delete("Delete from Image where link = #{link}")
    void deleteImageByLink(@Param("link") String link);

}
