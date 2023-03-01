package swyg.hollang.config

import feign.Request
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
class FeignClientConfig(val feignClientConfigProperties: FeignClientConfigProperties) {

    //타임아웃시 최대한 빨리 오류를 발생시켜 롤백을 하기 위함
    @Bean
    fun requestOptions(): Request.Options {
        return Request.Options(
            feignClientConfigProperties.connectTimeout,
            TimeUnit.MILLISECONDS,
            feignClientConfigProperties.readTimeout,
            TimeUnit.MILLISECONDS, false)
    }
}