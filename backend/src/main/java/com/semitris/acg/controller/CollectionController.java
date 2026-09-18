package com.semitris.acg.controller;

import com.semitris.acg.entity.Collection;
import com.semitris.acg.service.CollectionService;
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
 * @ClassName CollectionController
 * @Description 收藏/追番管理接口控制器
 * @Author SemiTris
 * @Date 2026年09月18日
 * @Version 1.0
 */
@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    /**
     * 新增收藏
     *
     * @param collection 收藏实体（userId、createdAt、updatedAt 无需前端传入；同番重复收藏将失败）
     * @return 统一响应结果
     */
    @PostMapping("/add")
    public R<Void> addCollection(@RequestBody Collection collection) {
        boolean success = collectionService.addCollection(collection);
        return success ? R.success() : R.error("收藏失败（该番已收藏或入参不合法）");
    }

    /**
     * 根据id删除收藏
     *
     * @param id 收藏id
     * @return 统一响应结果
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> removeCollection(@PathVariable("id") Long id) {
        boolean success = collectionService.removeCollection(id);
        return success ? R.success() : R.error("删除收藏失败");
    }

    /**
     * 修改收藏
     *
     * @param collection 收藏实体（必须携带 id；只更新非空字段）
     * @return 统一响应结果
     */
    @PutMapping("/update")
    public R<Void> editCollection(@RequestBody Collection collection) {
        boolean success = collectionService.editCollection(collection);
        return success ? R.success() : R.error("修改收藏失败");
    }

    /**
     * 按番查询收藏（详情页判断收藏状态，连表返回番剧 title/cover）
     *
     * @param animeId 番剧id
     * @return 统一响应结果（未收藏时 data 为 null）
     */
    @GetMapping("/findByAnime/{animeId}")
    public R<Collection> findByAnime(@PathVariable("animeId") Long animeId) {
        return R.success(collectionService.findByAnime(animeId));
    }

    /**
     * 收藏列表（连表返回番剧 title/cover，可按收藏类型筛选）
     *
     * @param listType 收藏类型（可选，1~5），不传 = 全部
     * @return 统一响应结果（携带收藏列表）
     */
    @GetMapping("/list")
    public R<List<Collection>> listCollection(
            @RequestParam(value = "listType", required = false) Integer listType) {
        return R.success(collectionService.listCollection(listType));
    }
}
