package com.semitris.acg.service;

import com.semitris.acg.entity.Collection;

import java.util.List;

/**
 * @ClassName CollectionService
 * @Description 收藏/追番服务接口
 * @Author SemiTris
 * @Date 2026年09月18日
 * @Version 1.0
 */
public interface CollectionService {

    //1.新增收藏(同番重复收藏返回失败；userId 后端填充)
    boolean addCollection(Collection collection);

    //2.删除收藏(根据id删除)
    boolean removeCollection(Long id);

    //3.修改收藏(动态更新非空字段；listType/favorite/progress 校验)
    boolean editCollection(Collection collection);

    //4.按番查询收藏(连表返回番剧 title/cover；未收藏返回 null)
    Collection findByAnime(Long animeId);

    //5.收藏列表(连表返回 title/cover；listType 可选过滤)
    List<Collection> listCollection(Integer listType);
}
