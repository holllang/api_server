package swyg.hollang.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@Profile("dev", "prod")
class WebConfig: WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/actuator/health")
            .allowedOrigins("*")
            .allowedMethods("GET")
            .allowedHeaders("*")
            .maxAge(3000)

        registry.addMapping("/**")
            .allowedOrigins("\${client.server.host}")
            .allowedMethods("*")
            .allowedHeaders("*")
            .maxAge(3000)
    }
}