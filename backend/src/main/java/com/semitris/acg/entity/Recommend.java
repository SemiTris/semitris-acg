package com.semitris.acg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName Recommend
 * @Description 推荐表实体类
 * @Author SemiTris
 * @Date 2026年08月27日 21:15
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recommend {

    private Long id;                //主键
    private Long userId;            //用户ID
    private Long animeId;           //番剧ID
    private String reason;          //推荐理由
    private String tags;            //推荐标签，如：治愈,神作
    private LocalDateTime createdAt;//推荐时间
    private LocalDateTime updatedAt;//修改时间

}


