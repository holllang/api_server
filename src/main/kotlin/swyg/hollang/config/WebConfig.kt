package swyg.hollang.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import swyg.hollang.interceptor.LogInterceptor

@Configuration
class WebConfig: WebMvcConfigurer {

    companion object {
        val LOG_INCLUDE_PATH = listOf("/tests/**", "/test-responses/**", "/recommendations/**", "/hobbies/**")
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LogInterceptor())
            .order(0)
            .addPathPatterns(LOG_INCLUDE_PATH)
            .excludePathPatterns("/**")
    }
}