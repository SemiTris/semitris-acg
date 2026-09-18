package com.semitris.acg.mapper;

import com.semitris.acg.entity.Anime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnimeMapper {

    //1.新增番剧
    int insertAnime(Anime anime);

    //2.删除番剧(根据id删除，子表由外键级联删除)
    int deleteAnimeById(Long id);

    //3.修改番剧信息
    int updateAnime(Anime anime);

    //4.查询番剧信息(根据id查询)
    Anime selectAnimeById(Long id);

    //5.多条件查询番剧(title模糊/type等值/status等值，分页由PageHelper完成，全空=全量)
    List<Anime> selectAnimeByCondition(@Param("title") String title,
                                       @Param("type") Integer type,
                                       @Param("status") Integer status);

    //6.全量列表(按id倒序)
    List<Anime> listAllAnime();
}
