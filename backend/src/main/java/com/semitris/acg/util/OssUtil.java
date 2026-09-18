package com.semitris.acg.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @ClassName OssUtil
 * @Description 阿里云 OSS 云存储工具（封面图片；密钥仅填本地 application.yaml 不入库）
 * @Author SemiTris
 * @Date 2026年09月18日
 * @Version 1.0
 * @see 未配置 oss.* 时 upload() 不应被调用（FileController 先以 isConfigured() 拦截）
 */
@Component
public class OssUtil {

    @Value("${oss.endpoint:}")
    private String endpoint;

    @Value("${oss.access-key-id:}")
    private String accessKeyId;

    @Value("${oss.access-key-secret:}")
    private String accessKeySecret;

    @Value("${oss.bucket-name:}")
    private String bucketName;

    /**
     * OSS 配置是否齐全（任一键为空视为未配置，上传应被拦截）
     *
     * @return 是否已完整配置
     */
    public boolean isConfigured() {
        return notEmpty(endpoint) && notEmpty(accessKeyId)
                && notEmpty(accessKeySecret) && notEmpty(bucketName);
    }

    /**
     * 上传文件到 OSS，返回公开访问直链 URL
     * <p>注意：调用前需确认 isConfigured()，本方法不校验配置，未配置将抛异常</p>
     *
     * @param objectName OSS 对象路径（如 cover/1690000000000.jpg）
     * @param input      文件输入流
     * @return 公开 URL = https://{bucket}.{endpoint}/{objectName}
     */
    public String upload(String objectName, InputStream input) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            ossClient.putObject(bucketName, objectName, input);
        } finally {
            ossClient.shutdown();
        }
        return "https://" + bucketName + "." + endpoint + "/" + objectName;
    }

    private boolean notEmpty(String s) {
        return s != null && !s.isEmpty();
    }
}
