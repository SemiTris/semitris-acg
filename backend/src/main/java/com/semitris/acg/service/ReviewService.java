package com.semitris.acg.service;

import com.semitris.acg.entity.Review;

import java.util.List;

/**
 * @ClassName ReviewService
 * @Description 评价服务接口
 * @Author SemiTris
 * @Date 2026年09月18日
 * @Version 1.0
 */
public interface ReviewService {

    //1.新增评价(rating 必填 1~10；userId 后端填充；写后同事务重算 anime.rating)
    boolean addReview(Review review);

    //2.修改评价(只更新非空 rating/content；anime_id 不可变更；写后同事务重算 anime.rating)
    boolean editReview(Review review);

    //3.删除评价(根据id删除；写后同事务重算 anime.rating)
    boolean removeReview(Long id);

    //4.按番查询评价列表(按 id 倒序)
    List<Review> listByAnime(Long animeId);
}
