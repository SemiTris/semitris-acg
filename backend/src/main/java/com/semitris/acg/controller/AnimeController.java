package com.semitris.acg.controller;

import com.github.pagehelper.PageInfo;
import com.semitris.acg.entity.Anime;
import com.semitris.acg.service.AnimeService;
import com.semitris.acg.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName AnimeController
 * @Description 番剧/漫画管理接口控制器
 * @Author SemiTris
 * @Date 2026年09月18日
 * @Version 1.0
 */
@RestController
@RequestMapping("/anime")
public class AnimeController {

    @Autowired
    private AnimeService animeService;

    /**
     * 新增番剧
     *
     * @param anime 番剧实体（id、createdAt、updatedAt 无需前端传入）
     * @return 统一响应结果
     */
    @PostMapping("/add")
    public R<Void> addAnime(@RequestBody Anime anime) {
        boolean success = animeService.addAnime(anime);
        return success ? R.success() : R.error("新增番剧失败");
    }

    /**
     * 根据id删除番剧
     *
     * @param id 番剧id
     * @return 统一响应结果
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> removeAnime(@PathVariable("id") Long id) {
        boolean success = animeService.removeAnime(id);
        return success ? R.success() : R.error("删除番剧失败");
    }

    /**
     * 修改番剧信息
     *
     * @param anime 番剧实体（必须携带 id；空字段不更新）
     * @return 统一响应结果
     */
    @PutMapping("/update")
    public R<Void> editAnime(@RequestBody Anime anime) {
        boolean success = animeService.editAnime(anime);
        return success ? R.success() : R.error("修改番剧失败");
    }

    /**
     * 根据id查询番剧信息
     *
     * @param id 番剧id
     * @return 统一响应结果
     */
    @GetMapping("/findById/{id}")
    public R<Anime> getAnime(@PathVariable("id") Long id) {
        Anime anime = animeService.getAnime(id);
        return anime != null ? R.success(anime) : R.error("番剧不存在");
    }

    /**
     * 全量番剧列表（按id倒序，供下拉框使用）
     *
     * @return 统一响应结果（携带番剧列表）
     */
    @GetMapping("/list")
    public R<List<Anime>> listAnime() {
        return R.success(animeService.listAnime());
    }

    /**
     * 分页多条件查询番剧（标题模糊 / 类型等值 / 状态等值）
     *
     * @param pageNum  页码，默认1
     * @param pageSize 每页条数，默认5
     * @param title    标题（可选，模糊查询）
     * @param type     类型（可选，1=动画 2=漫画）
     * @param status   状态（可选，0=连载中 1=已完结）
     * @return 统一响应结果（携带分页信息 PageInfo）
     */
    @GetMapping("/page")
    public R<PageInfo<Anime>> getAnimePage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "status", required = false) Integer status) {
        return R.success(animeService.getAnimePage(pageNum, pageSize, title, type, status));
    }
}
