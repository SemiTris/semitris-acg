package com.semitris.acg.service;

import com.semitris.acg.entity.Recommend;

import java.util.List;

/**
 * @ClassName RecommendService
 * @Description 推荐/安利服务接口
 * @Author SemiTris
 * @Date 2026年09月18日
 * @Version 1.0
 */
public interface RecommendService {

    //1.新增推荐(animeId 必填；userId 后端填充)
    boolean addRecommend(Recommend recommend);

    //2.删除推荐(根据id删除)
    boolean removeRecommend(Long id);

    //3.修改推荐(只更新非空 reason/tags；animeId 不可变更；无可更新字段拒绝)
    boolean editRecommend(Recommend recommend);

    //4.安利墙列表(连表番剧 title/cover，按 id 倒序)
    List<Recommend> listRecommend();
}
