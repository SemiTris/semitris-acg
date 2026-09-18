package com.semitris.acg.service.impl;

import com.semitris.acg.entity.Anime;
import com.semitris.acg.entity.Collection;
import com.semitris.acg.mapper.AnimeMapper;
import com.semitris.acg.mapper.CollectionMapper;
import com.semitris.acg.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CollectionServiceImpl
 * @Description 收藏/追番服务实现类
 * @Author SemiTris
 * @Date 2026年09月18日
 * @Version 1.0
 */
@Service
public class CollectionServiceImpl implements CollectionService {

    // 单人站点固定用户ID，后端强制填充，不进 API
    private static final Long DEFAULT_USER_ID = 1L;

    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private AnimeMapper animeMapper;

    /**
     * 新增收藏
     * <p>userId 后端强制 DEFAUT_USER_ID；同番重复收藏返回失败（唯一约束，不抛异常）；listType 1~5、favorite 0/1、progress 不超过 episodes 校验</p>
     *
     * @param collection 收藏实体（id、createdAt、updatedAt、userId 无需前端传入）
     * @return 是否新增成功（重复收藏返回 false）
     */
    @Override
    public boolean addCollection(Collection collection) {
        if (collection == null || collection.getAnimeId() == null) {
            return false;
        }
        //listType 1~5
        if (collection.getListType() != null
                && (collection.getListType() < 1 || collection.getListType() > 5)) {
            return false;
        }
        //favorite 0/1
        if (collection.getFavorite() != null
                && collection.getFavorite() != 0 && collection.getFavorite() != 1) {
            return false;
        }
        //progress 校验上限用动画 episodes
        if (collection.getProgress() != null && !validProgress(collection.getAnimeId(), collection.getProgress())) {
            return false;
        }
        //同番同用户重复收藏（唯一约束）→ 返回失败
        if (collectionMapper.selectByAnimeAndUser(collection.getAnimeId(), DEFAULT_USER_ID) != null) {
            return false;
        }
        //userId 后端强制填充
        collection.setUserId(DEFAULT_USER_ID);
        return collectionMapper.insertCollection(collection) > 0;
    }

    /**
     * 根据id删除收藏
     *
     * @param id 收藏id
     * @return 是否删除成功
     */
    @Override
    public boolean removeCollection(Long id) {
        return collectionMapper.deleteCollectionById(id) > 0;
    }

    /**
     * 修改收藏
     * <p>只更新非空字段；listType 1~5、favorite 0/1 校验；progress 校验 0<=progress<=episodes（用请求体 animeId 优先，未携带则用原记录 anime_id）；user_id/anime_id 不可变更</p>
     *
     * @param collection 收藏实体（必须携带id）
     * @return 是否修改成功
     */
    @Override
    public boolean editCollection(Collection collection) {
        if (collection == null || collection.getId() == null) {
            return false;
        }
        //无可更新字段（listType/progress/favorite 均为空）→ 拒绝，避免更新空 set 导致 SQL 错误
        if (collection.getListType() == null && collection.getProgress() == null
                && collection.getFavorite() == null) {
            return false;
        }
        //listType 1~5
        if (collection.getListType() != null
                && (collection.getListType() < 1 || collection.getListType() > 5)) {
            return false;
        }
        //favorite 0/1
        if (collection.getFavorite() != null
                && collection.getFavorite() != 0 && collection.getFavorite() != 1) {
            return false;
        }
        //progress 校验上限：非空时需 0<=progress<=episodes；优先用请求体 animeId，未携带则查原记录的原 anime_id，拿不到（原记录不存在）拒绝
        if (collection.getProgress() != null) {
            Long animeId = collection.getAnimeId();
            if (animeId == null) {
                //请求体未携带 anime_id：用原收藏记录的原 anime_id 校验
                Collection old = collectionMapper.selectCollectionById(collection.getId());
                if (old == null) {
                    //原收藏不存在，无法取番归属，拒绝（该 id 本就不应更新）
                    return false;
                }
                animeId = old.getAnimeId();
            }
            if (!validProgress(animeId, collection.getProgress())) {
                return false;
            }
        }
        //userId 后端强制填充（update 不写 user_id，语义保持一致）
        collection.setUserId(DEFAULT_USER_ID);
        return collectionMapper.updateCollection(collection) > 0;
    }

    /**
     * 按番查询收藏（连表返回番剧 title/cover）
     *
     * @param animeId 番剧id
     * @return 收藏实体（连表），未收藏返回 null
     */
    @Override
    public Collection findByAnime(Long animeId) {
        return collectionMapper.selectByAnimeAndUser(animeId, DEFAULT_USER_ID);
    }

    /**
     * 收藏列表（连表返回番剧 title/cover，listType 可选过滤）
     *
     * @param listType 收藏类型（可选，1~5），不传 = 全部
     * @return 收藏列表（按 id 倒序）
     */
    @Override
    public List<Collection> listCollection(Integer listType) {
        return collectionMapper.listByUser(DEFAULT_USER_ID, listType);
    }

    /**
     * 校验收藏进度：0 <= progress <= anime.episodes
     * <p>番不存在时交由数据库外键兜底，不在此主动失败</p>
     *
     * @param animeId  番剧id
     * @param progress 进度
     * @return 校验是否通过
     */
    private boolean validProgress(Long animeId, Integer progress) {
        if (progress < 0) {
            return false;
        }
        Anime anime = animeMapper.selectAnimeById(animeId);
        if (anime == null || anime.getEpisodes() == null) {
            //番不存在或 episerver 未知 → 交由数据库外键/默认兜底
            return true;
        }
        return progress <= anime.getEpisodes();
    }
}
