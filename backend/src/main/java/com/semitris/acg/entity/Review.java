package com.semitris.acg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName Review
 * @Description 评价表实体类
 * @Author SemiTris
 * @Date 2026年08月27日 21:15
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    private Long id;                //主键
    private Long userId;            //用户ID
    private Long animeId;           //番剧ID
    private Integer rating;         //评分（1 ~ 10）
    private String content;         //评价内容
    private LocalDateTime createdAt;//评价时间
    private LocalDateTime updatedAt;//修改时间

    private String title;           //番剧标题（连表非持久化字段，不入库）
    private String cover;           //番剧封面URL（连表非持久化字段，不入库）

}


