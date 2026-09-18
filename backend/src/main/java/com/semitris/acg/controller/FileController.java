package com.semitris.acg.controller;

import com.semitris.acg.util.OssUtil;
import com.semitris.acg.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @ClassName FileController
 * @Description 封面文件上传接口控制器（阿里云 OSS 云存储，返回公开直链）
 * @Author SemiTris
 * @Date 2026年09月18日
 * @Version 1.0
 * @see /file/download 已弃用（OSS 直链即公开 URL，无本地回显）
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private OssUtil ossUtil;

    /**
     * 上传封面图片到 OSS，返回公开直链 URL
     * <p>表单字段名必须为 file（multipart）；objectName = cover/{时间戳}.{小写扩展名}</p>
     *
     * @param file 上传的图片文件
     * @return 统一响应结果（成功携带 OSS 直链 URL）
     */
    @PostMapping("/upload")
    public R<String> upload(@RequestParam("file") MultipartFile file) {
        //空文件
        if (file == null || file.isEmpty()) {
            return R.error("文件为空");
        }
        //未配置 OSS 密钥
        if (!ossUtil.isConfigured()) {
            return R.error("上传服务未配置");
        }
        //objectName = cover/{时间戳}.{小写扩展名}
        String original = file.getOriginalFilename();
        String ext = "";
        if (original != null && original.contains(".")) {
            ext = original.substring(original.lastIndexOf(".")).toLowerCase();
        }
        String objectName = "cover/" + System.currentTimeMillis() + ext;
        try (InputStream in = file.getInputStream()) {
            String url = ossUtil.upload(objectName, in);
            return R.success("上传成功", url);
        } catch (Exception e) {
            return R.error("上传失败");
        }
    }
}
