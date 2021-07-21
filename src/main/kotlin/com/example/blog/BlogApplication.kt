package com.example.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

// おまじない実行zone
//// EnableConfigurationPropertiesは
// kotlin+ sprngbootでpropertiesを管理するためのベストプラクティス通りに実装
//// https://spring.pleiades.io/guides/tutorials/spring-boot-kotlin/
@SpringBootApplication
@EnableConfigurationProperties(BlogProperties::class)
class BlogApplication

fun main(args: Array<String>) {
	runApplication<BlogApplication>(*args)
}
