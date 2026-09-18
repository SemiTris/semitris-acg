package com.semitris.acg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * @ClassName Collection
 * @Description 收藏表实体类
 * @Author SemiTris
 * @Date 2026年08月27日 21:14
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("userCollection")
public class Collection {

    private Long id;                //主键
    private Long userId;            //用户ID
    private Long animeId;           //番剧ID
    private Integer listType;       //收藏类型：1=想看 2=在看 3=已看完 4=搁置 5=最爱
    private Integer progress;       //看到第几集/话
    private Integer favorite;       //是否加精收藏：0=否 1=是
    private LocalDateTime createdAt;//收藏时间
    private LocalDateTime updatedAt;//更新时间

    private String title;           //番剧标题（连表非持久化字段，不入库）
    private String cover;           //番剧封面URL（连表非持久化字段，不入库）

}

