package com.semitris.acg.mapper;

import com.semitris.acg.entity.Review;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {

    //1.新增评价（userId 由 Service 强制填充 DEFAULT_USER_ID）
    int insertReview(Review review);

    //2.修改评价(动态更新 rating/content；anime_id 不可变更，由 Service 保证取原值)
    int updateReview(Review review);

    //3.删除评价(根据id删除)
    int deleteReviewById(Long id);

    //4.按番查询评价列表(按 id 倒序)
    List<Review> selectByAnimeId(Long animeId);

    //5.根据id查询评价(取原 anime_id 用于重算用)
    Review selectById(Long id);
}
