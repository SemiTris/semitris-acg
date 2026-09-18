package com.semitris.acg.service;

import com.github.pagehelper.PageInfo;
import com.semitris.acg.entity.Anime;

import java.util.List;

/**
 * @ClassName AnimeService
 * @Description 番剧/漫画服务接口
 * @Author SemiTris
 * @Date 2026年09月18日
 * @Version 1.0
 */
public interface AnimeService {

    //1.新增番剧
    boolean addAnime(Anime anime);

    //2.删除番剧(根据id删除，子表由外键级联删除)
    boolean removeAnime(Long id);

    //3.修改番剧信息
    boolean editAnime(Anime anime);

    //4.查询番剧信息(根据id查询)
    Anime getAnime(Long id);

    //5.全量列表(按id倒序)
    List<Anime> listAnime();

    //6.分页多条件查询番剧(title模糊/type等值/status等值)
    PageInfo<Anime> getAnimePage(int pageNum, int pageSize, String title, Integer type, Integer status);
}
