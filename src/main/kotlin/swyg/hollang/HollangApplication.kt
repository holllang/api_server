package swyg.hollang

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@ConfigurationPropertiesScan("swyg.hollang.config")
@EnableFeignClients
@SpringBootApplication
class HollangApplication

fun main(args: Array<String>) {
    runApplication<HollangApplication>(*args)
}
