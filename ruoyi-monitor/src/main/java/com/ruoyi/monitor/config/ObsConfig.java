package com.ruoyi.monitor.config;

import com.obs.services.ObsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObsConfig {

    // 您可以通过环境变量获取访问密钥AK/SK，也可以使用其他外部引入方式传入。如果使用硬编码可能会存在泄露风险。
    // 您可以登录访问管理控制台获取访问密钥AK/SK
    private String ak = System.getProperty("ACCESS_KEY_ID");
    private String sk = System.getProperty("SECRET_ACCESS_KEY_ID");
    // 【可选】如果使用临时AK/SK和SecurityToken访问OBS，同样建议您尽量避免使用硬编码，以降低信息泄露风险。
    // 您可以通过环境变量获取访问密钥AK/SK/SecurityToken，也可以使用其他外部引入方式传入。
    // String securityToken = System.getenv("SECURITY_TOKEN");
    // endpoint填写桶所在的endpoint, 此处以华北-北京四为例，其他地区请按实际情况填写。
    private String endPoint = "http://obs.cn-south-1.myhuaweicloud.com";
    // 您可以通过环境变量获取endPoint，也可以使用其他外部引入方式传入。
    //String endPoint = System.getenv("ENDPOINT");

    @Bean
    public ObsClient obsClient() {
        // 创建ObsClient实例
        // 使用永久AK/SK初始化客户端
        return new ObsClient(ak, sk, endPoint);
        // 使用临时AK/SK和SecurityToken初始化客户端
        // ObsClient obsClient = new ObsClient(ak, sk, securityToken, endPoint);
    }
}
