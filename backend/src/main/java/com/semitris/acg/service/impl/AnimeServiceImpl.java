package com.semitris.acg.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.semitris.acg.entity.Anime;
import com.semitris.acg.mapper.AnimeMapper;
import com.semitris.acg.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName AnimeServiceImpl
 * @Description 番剧/漫画服务实现类
 * @Author SemiTris
 * @Date 2026年09月18日
 * @Version 1.0
 */
@Service
public class AnimeServiceImpl implements AnimeService {

    @Autowired
    private AnimeMapper animeMapper;

    /**
     * 新增番剧
     * <p>title 必填；type 需为 1/2、status 需为 0/1；若直接提交 rating 需在 0.0~10.0 之间（冗余字段）</p>
     *
     * @param anime 番剧实体（id、createdAt、updatedAt 无需前端传入）
     * @return 是否新增成功
     */
    @Override
    public boolean addAnime(Anime anime) {
        //title 必填
        if (anime == null || anime.getTitle() == null || anime.getTitle().isEmpty()) {
            return false;
        }
        //type 需为 1/2
        if (anime.getType() != null && anime.getType() != 1 && anime.getType() != 2) {
            return false;
        }
        //status 需为 0/1
        if (anime.getStatus() != null && anime.getStatus() != 0 && anime.getStatus() != 1) {
            return false;
        }
        //rating 若传入需在 0.0~10.0 之间
        if (anime.getRating() != null
                && (anime.getRating().compareTo(BigDecimal.ZERO) < 0
                || anime.getRating().compareTo(new BigDecimal("10")) > 0)) {
            return false;
        }
        return animeMapper.insertAnime(anime) > 0;
    }

    /**
     * 根据id删除番剧
     * <p>子表 collection/review/recommend 由外键 ON DELETE CASCADE 级联清理，Service 不手工删子表</p>
     *
     * @param id 番剧id
     * @return 是否删除成功
     */
    @Override
    public boolean removeAnime(Long id) {
        return animeMapper.deleteAnimeById(id) > 0;
    }

    /**
     * 修改番剧信息
     * <p>动态更新，空值字段不更新（updated_at 交给数据库自动刷）；传入的 type/status/rating 同样做合法性校验</p>
     *
     * @param anime 番剧实体（必须携带id）
     * @return 是否修改成功
     */
    @Override
    public boolean editAnime(Anime anime) {
        if (anime == null || anime.getId() == null) {
            return false;
        }
        //type 需为 1/2
        if (anime.getType() != null && anime.getType() != 1 && anime.getType() != 2) {
            return false;
        }
        //status 需为 0/1
        if (anime.getStatus() != null && anime.getStatus() != 0 && anime.getStatus() != 1) {
            return false;
        }
        //rating 若传入需在 0.0~10.0 之间
        if (anime.getRating() != null
                && (anime.getRating().compareTo(BigDecimal.ZERO) < 0
                || anime.getRating().compareTo(new BigDecimal("10")) > 0)) {
            return false;
        }
        return animeMapper.updateAnime(anime) > 0;
    }

    /**
     * 根据id查询番剧信息
     *
     * @param id 番剧id
     * @return 番剧实体，不存在时返回null
     */
    @Override
    public Anime getAnime(Long id) {
        return animeMapper.selectAnimeById(id);
    }

    /**
     * 全量列表（按id倒序，供下拉框使用）
     *
     * @return 番剧列表
     */
    @Override
    public List<Anime> listAnime() {
        return animeMapper.listAllAnime();
    }

    /**
     * 分页多条件查询番剧（title 模糊 / type 等值 / status 等值，条件全空 = 全量）
     *
     * @param pageNum  页码（从1开始）
     * @param pageSize 每页条数
     * @param title    标题（可选，模糊）
     * @param type     类型（可选，等值 1/2）
     * @param status   状态（可选，等值 0/1）
     * @return 分页结果 PageInfo
     */
    @Override
    public PageInfo<Anime> getAnimePage(int pageNum, int pageSize, String title, Integer type, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<Anime> animes = animeMapper.selectAnimeByCondition(title, type, status);
        return new PageInfo<>(animes);
    }
}
