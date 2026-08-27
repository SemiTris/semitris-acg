package com.semitris.acg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName Anime
 * @Description 动画表实体类
 * @Author SemiTris
 * @Date 2026年08月27日 21:13
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Anime {

    private Long id;                //主键
    private String title;           //中文标题
    private String originalTitle;   //原名
    private String cover;           //封面图片URL
    private Integer type;           //类型：1=动画 2=漫画
    private String genre;           //类型标签，逗号分隔
    private Integer episodes;       //集数/话数
    private Integer status;         //连载状态：0=连载中 1=已完结
    private Integer year;           //播出/连载年份
    private BigDecimal rating;      //平均评分(0.0 ~ 10.0)
    private String synopsis;        //简介
    private LocalDateTime createdAt;//录入时间
    private LocalDateTime updatedAt;//修改时间

}