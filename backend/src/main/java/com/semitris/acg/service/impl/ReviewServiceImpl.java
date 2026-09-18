package com.semitris.acg.service.impl;

import com.semitris.acg.entity.Review;
import com.semitris.acg.mapper.AnimeMapper;
import com.semitris.acg.mapper.ReviewMapper;
import com.semitris.acg.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName ReviewServiceImpl
 * @Description 评价服务实现类
 * @Author SemiTris
 * @Date 2026年09月18日
 * @Version 1.0
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    // 单人站点固定用户ID，后端强制填充，不进 API
    private static final Long DEFAULT_USER_ID = 1L;

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private AnimeMapper animeMapper;

    /**
     * 新增评价
     * <p>rating 必填且须 1~10；userId 后端强制填充；成功后在<b>同一事务</b>内重算该番 anime.rating</p>
     *
     * @param review 评价实体（id、createdAt、updatedAt、userId 无需前端传入）
     * @return 是否新增成功
     */
    @Override
    @Transactional
    public boolean addReview(Review review) {
        //rating 必填且 1~10
        if (review == null || review.getAnimeId() == null
                || review.getRating() == null || review.getRating() < 1 || review.getRating() > 10) {
            return false;
        }
        //userId 后端强制填充
        review.setUserId(DEFAULT_USER_ID);
        if (reviewMapper.insertReview(review) <= 0) {
            return false;
        }
        //同事务重算 anime.rating = AVG(review.rating)
        animeMapper.updateAnimeRating(review.getAnimeId());
        return true;
    }

    /**
     * 修改评价
     * <p>只更新非空字段 rating/content；anime_id 不可变更（重算用数据库原值）；rating 若传入须 1~10</p>
     *
     * @param review 评价实体（必须携带id）
     * @return 是否修改成功
     */
    @Override
    @Transactional
    public boolean editReview(Review review) {
        if (review == null || review.getId() == null) {
            return false;
        }
        //rating 若传入须 1~10
        if (review.getRating() != null && (review.getRating() < 1 || review.getRating() > 10)) {
            return false;
        }
        //无可更新字段（rating 与 content 均为空）→ 拒绝，避免更新空 set 导致 SQL 错误
        if (review.getRating() == null && review.getContent() == null) {
            return false;
        }
        //update 不允许变更 anime_id——先取数据库原值用于重算
        Review old = reviewMapper.selectById(review.getId());
        if (old == null) {
            return false;
        }
        if (reviewMapper.updateReview(review) <= 0) {
            return false;
        }
        //同事务重算原番 anime.rating
        animeMapper.updateAnimeRating(old.getAnimeId());
        return true;
    }

    /**
     * 删除评价
     * <p>删除后在同一事务内重算该番 anime.rating；该番无评价时 rating 置 NULL</p>
     *
     * @param id 评价id
     * @return 是否删除成功
     */
    @Override
    @Transactional
    public boolean removeReview(Long id) {
        //先取原 anime_id 用于重算
        Review old = reviewMapper.selectById(id);
        if (reviewMapper.deleteReviewById(id) <= 0) {
            return false;
        }
        //同事务重算原番 anime.rating
        if (old != null) {
            animeMapper.updateAnimeRating(old.getAnimeId());
        }
        return true;
    }

    /**
     * 按番查询评价列表（按 id 倒序）
     *
     * @param animeId 番剧id
     * @return 评价列表
     */
    @Override
    public List<Review> listByAnime(Long animeId) {
        return reviewMapper.selectByAnimeId(animeId);
    }
}
