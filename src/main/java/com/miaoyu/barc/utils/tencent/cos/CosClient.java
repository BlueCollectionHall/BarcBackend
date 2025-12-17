package com.miaoyu.barc.utils.tencent.cos;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 手动配置腾讯云COS对象存储客户端（Bean对象）
 * 当前是DEV方式
 * ！！！ 生产环境时需要将秘密ID和密钥通过密文解密 ！！！
 * */

@Configuration
@Component
public class CosClient {
    @Autowired
    private CosConfig cosConfig;

    public COSClient cosClient() {
        COSCredentials cred = new BasicCOSCredentials(
                cosConfig.getSecretId(),
                cosConfig.getSecretKey()
        );
        ClientConfig clientConfig = new ClientConfig(new Region(cosConfig.getRegion()));
        return new COSClient(cred, clientConfig);
    }
}
