package com.semitris.acg.controller;

import com.github.pagehelper.PageInfo;
import com.semitris.acg.entity.Review;
import com.semitris.acg.service.ReviewService;
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
 * @ClassName ReviewController
 * @Description 评价管理接口控制器
 * @Author SemiTris
 * @Date 2026年09月18日
 * @Version 1.0
 */
@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    /**
     * 新增评价（成功后同事务重算该番 anime.rating）
     *
     * @param review 评价实体（userId、createdAt、updatedAt 无需前端传入）
     * @return 统一响应结果
     */
    @PostMapping("/add")
    public R<Void> addReview(@RequestBody Review review) {
        boolean success = reviewService.addReview(review);
        return success ? R.success() : R.error("新增评价失败");
    }

    /**
     * 修改评价（只更新非空字段 rating/content；anime_id 不可变更；成功后同事务重算 anime.rating）
     *
     * @param review 评价实体（必须携带 id）
     * @return 统一响应结果
     */
    @PutMapping("/update")
    public R<Void> editReview(@RequestBody Review review) {
        boolean success = reviewService.editReview(review);
        return success ? R.success() : R.error("修改评价失败");
    }

    /**
     * 根据id删除评价（删除后同事务重算该番 anime.rating；无评价时置 NULL）
     *
     * @param id 评价id
     * @return 统一响应结果
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> removeReview(@PathVariable("id") Long id) {
        boolean success = reviewService.removeReview(id);
        return success ? R.success() : R.error("删除评价失败");
    }

    /**
     * 按番查询评价列表（按 id 倒序）
     *
     * @param animeId 番剧id
     * @return 统一响应结果（携带评价列表）
     */
    @GetMapping("/listByAnime/{animeId}")
    public R<List<Review>> listByAnime(@PathVariable("animeId") Long animeId) {
        return R.success(reviewService.listByAnime(animeId));
    }

    /**
     * 分页查询评价列表（连表番剧 title/cover，animeId 可选过滤）
     *
     * @param pageNum  页码，默认1
     * @param pageSize 每页条数，默认5
     * @param animeId  番剧id（可选，仅返回该番评价）
     * @return 统一响应结果（携带分页信息 PageInfo）
     */
    @GetMapping("/page")
    public R<PageInfo<Review>> pageReview(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "animeId", required = false) Long animeId) {
        return R.success(reviewService.pageReview(pageNum, pageSize, animeId));
    }
}
