package com.semitris.acg.controller;

import com.semitris.acg.entity.Recommend;
import com.semitris.acg.service.RecommendService;
import com.semitris.acg.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName RecommendController
 * @Description 推荐/安利管理接口控制器
 * @Author SemiTris
 * @Date 2026年09月18日
 * @Version 1.0
 */
@RestController
@RequestMapping("/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    /**
     * 新增推荐
     *
     * @param recommend 推荐实体（userId、createdAt、updatedAt 无需前端传入）
     * @return 统一响应结果
     */
    @PostMapping("/add")
    public R<Void> addRecommend(@RequestBody Recommend recommend) {
        boolean success = recommendService.addRecommend(recommend);
        return success ? R.success() : R.error("新增推荐失败");
    }

    /**
     * 根据id删除推荐
     *
     * @param id 推荐id
     * @return 统一响应结果
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> removeRecommend(@PathVariable("id") Long id) {
        boolean success = recommendService.removeRecommend(id);
        return success ? R.success() : R.error("删除推荐失败");
    }

    /**
     * 修改推荐
     *
     * @param recommend 推荐实体（必须携带 id；只更新非空字段；animeId 不可变更）
     * @return 统一响应结果
     */
    @PutMapping("/update")
    public R<Void> editRecommend(@RequestBody Recommend recommend) {
        boolean success = recommendService.editRecommend(recommend);
        return success ? R.success() : R.error("修改推荐失败");
    }

    /**
     * 安利墙列表（连表返回番剧 title/cover，按 id 倒序）
     *
     * @return 统一响应结果（携带推荐列表）
     */
    @GetMapping("/list")
    public R<List<Recommend>> listRecommend() {
        return R.success(recommendService.listRecommend());
    }
}
