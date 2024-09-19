package kr.nyamnyam.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BucketConfig {
    private final String endpoint = "https://kr.object.ncloudstorage.com";
    private final String regionName = "kr-standard";

    @Value("${naver.storage.accessKey}")
    private String accessKey;

    @Value("${naver.storage.secretKey}")
    private String secretKey;

    @Value("${naver.storage.bucket}")
    private String bucketName;

}
