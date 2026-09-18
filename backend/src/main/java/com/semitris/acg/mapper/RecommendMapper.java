package com.semitris.acg.mapper;

import com.semitris.acg.entity.Recommend;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecommendMapper {

    //1.新增推荐（userId 由 Service 强制填充 DEFAULT_USER_ID）
    int insertRecommend(Recommend recommend);

    //2.删除推荐(根据id删除)
    int deleteRecommendById(Long id);

    //3.修改推荐(动态更新 reason/tags；anime_id/user_id 不可变更)
    int updateRecommend(Recommend recommend);

    //4.安利墙列表(连表番剧 title/cover，按 id 倒序)
    List<Recommend> listRecommend();
}
