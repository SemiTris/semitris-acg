package com.semitris.acg.service.impl;

import com.semitris.acg.entity.Recommend;
import com.semitris.acg.mapper.RecommendMapper;
import com.semitris.acg.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RecommendServiceImpl
 * @Description 推荐/安利服务实现类
 * @Author SemiTris
 * @Date 2026年09月18日
 * @Version 1.0
 */
@Service
public class RecommendServiceImpl implements RecommendService {

    // 单人站点固定用户ID，后端强制填充，不进 API
    private static final Long DEFAULT_USER_ID = 1L;

    @Autowired
    private RecommendMapper recommendMapper;

    /**
     * 新增推荐
     * <p>animeId 必填；userId 后端强制填充；reason/tags 可选</p>
     *
     * @param recommend 推荐实体（id、createdAt、updatedAt、userId 无需前端传入）
     * @return 是否新增成功
     */
    @Override
    public boolean addRecommend(Recommend recommend) {
        if (recommend == null || recommend.getAnimeId() == null) {
            return false;
        }
        //userId 后端强制填充
        recommend.setUserId(DEFAULT_USER_ID);
        return recommendMapper.insertRecommend(recommend) > 0;
    }

    /**
     * 根据id删除推荐
     *
     * @param id 推荐id
     * @return 是否删除成功
     */
    @Override
    public boolean removeRecommend(Long id) {
        return recommendMapper.deleteRecommendById(id) > 0;
    }

    /**
     * 修改推荐
     * <p>只更新非空字段 reason/tags；anime_id 不可变更；无可更新字段（reason/tags 均为空）拒绝</p>
     *
     * @param recommend 推荐实体（必须携带id）
     * @return 是否修改成功
     */
    @Override
    public boolean editRecommend(Recommend recommend) {
        if (recommend == null || recommend.getId() == null) {
            return false;
        }
        //无可更新字段（reason/tags 均为空）→ 拒绝，避免动态 SQL 空 set
        if (recommend.getReason() == null && recommend.getTags() == null) {
            return false;
        }
        //userId 后端强制填充（update 不写 user_id，语义保持一致）
        recommend.setUserId(DEFAULT_USER_ID);
        return recommendMapper.updateRecommend(recommend) > 0;
    }

    /**
     * 安利墙列表（连表返回番剧 title/cover，按 id 倒序）
     *
     * @return 推荐列表
     */
    @Override
    public List<Recommend> listRecommend() {
        return recommendMapper.listRecommend();
    }
}
