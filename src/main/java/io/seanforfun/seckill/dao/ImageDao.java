package io.seanforfun.seckill.dao;

import io.seanforfun.seckill.entity.domain.Image;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
    @Insert("Insert into Image (id, link, name, source, type, associateId, deleteHash) " +
            "values (#{id}, #{link}, #{name}, #{source}, #{type}, #{associateId}, #{deleteHash})")
    void saveImageInfo(Image image);

    // Load image information.

    // Update image information.

    // Delete image information.
    @Delete("Delete from Image where link = #{link}")
    void deleteImageByLink(@Param("link") String link);
}
