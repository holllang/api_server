package swyg.hollang.aop

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import swyg.hollang.trace.logtrace.LogTrace

@Configuration
class AopConfig {

    @Bean
    @Profile("local")
    fun logTraceAspect(logTrace: LogTrace): LogTraceAspect {
        return LogTraceAspect(logTrace)
    }
}