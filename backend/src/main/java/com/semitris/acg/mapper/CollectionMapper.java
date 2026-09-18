package com.semitris.acg.mapper;

import com.semitris.acg.entity.Collection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CollectionMapper {

    //1.新增收藏（userId 由 Service 强制填充 DEFAULT_USER_ID）
    int insertCollection(Collection collection);

    //2.删除收藏(根据id删除)
    int deleteCollectionById(Long id);

    //3.修改收藏(动态更新，只改非空字段；user_id/anime_id 不更新)
    int updateCollection(Collection collection);

    //4.按番+按用户查询收藏(连表返回番剧 title/cover；用于判重与详情页收藏状态)
    Collection selectByAnimeAndUser(@Param("animeId") Long animeId,
                                    @Param("userId") Long userId);

    //5.按用户查询收藏列表(连表返回 title/cover，listType 可选过滤，按 id 倒序)
    List<Collection> listByUser(@Param("userId") Long userId,
                                @Param("listType") Integer listType);
}
