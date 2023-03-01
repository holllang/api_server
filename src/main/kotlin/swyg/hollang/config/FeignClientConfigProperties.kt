package swyg.hollang.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("feign.client.config.default")
class FeignClientConfigProperties {
    var connectTimeout: Long = 5000
    var readTimeout: Long = 10000
}