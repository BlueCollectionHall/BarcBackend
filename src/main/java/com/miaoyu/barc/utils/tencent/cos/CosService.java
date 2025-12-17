package com.miaoyu.barc.utils.tencent.cos;

import com.miaoyu.barc.annotation.RequireUserAndPermissionAnno;
import com.miaoyu.barc.response.ChangeR;
import com.miaoyu.barc.utils.GenerateUUID;
import com.miaoyu.barc.utils.J;
import com.qcloud.cos.model.CopyObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Date;

/**
 * 腾讯云COS对象存储业务层
 * ！！！注意数据读写安全性和配置关系！！！
 * ！！！本业务层独立于其它接口性业务层，使用J.class返回信息，code：0正确、1错误！！！
 * */
@Service
@Slf4j
public class CosService {
    @Autowired
    private CosClient cosClient;
    @Autowired
    private CosConfig cosConfig;

    /**
     * 上传文件到COS
     * @param file 文件
     * @param key 存储路径
     * @return 文件信息
     */
    public J uploadFile (MultipartFile file, String key) {
        // 文件原始名
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            fileName = new GenerateUUID().getUuid32u();
        }
        // 获取扩展名
        String extension = StringUtils.getFilenameExtension(fileName);
        // 生成唯一文件名 路径/时间戳-文件名.扩展名
        String uniqueKeyFilename =
                key + new Date().getTime() + "-" + fileName + (extension != null? "." + extension: "");
        try {
            // 上传COS
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(file.getSize());
            meta.setContentType(file.getContentType());
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    cosConfig.getBucketName(),
                    uniqueKeyFilename,
                    file.getInputStream(),
                    meta
            );
            cosClient.cosClient().putObject(putObjectRequest);
            // 构建返回路径
            String url = cosConfig.getBaseUrl() + uniqueKeyFilename;
            return new J(0, "文件上传成功", url);
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new RuntimeException("文件上传失败");
        }
    }

    /**
     * 删除COS文件
     * @param filePathName 文件路径+文件名
     * @return 删除信息
     */
    public J deleteFile (String filePathName) {
        if (filePathName == null || filePathName.isEmpty()) {
            return new J(1, "文件地址为空", "文件地址为空");
        }
        try {
            cosClient.cosClient().deleteObject(cosConfig.getBucketName(), filePathName);
            return new ChangeR().udu(true, 2);
        } catch (Exception e) {
            log.error("删除COS文件失败: {}", filePathName, e);
            return new ChangeR().udu(false, 2);
        }
    }

    /**
     * 移动文件（或从临时目录移动到正式目录）
     * @param sourceKey 源路径
     * @param targetKey 目标路径
     */
    public void moveFile(String sourceKey, String targetKey) {
        try {
            // 复制文件
            CopyObjectRequest copyObjectRequest = new CopyObjectRequest(
                    cosConfig.getBucketName(), sourceKey,
                    cosConfig.getBucketName(), targetKey
            );
            cosClient.cosClient().copyObject(copyObjectRequest);

            // 删除源文件
            deleteFile(sourceKey);

        } catch (Exception e) {
            log.error("移动文件失败: {} -> {}", sourceKey, targetKey, e);
            throw new RuntimeException("文件移动失败");
        }
    }
}
