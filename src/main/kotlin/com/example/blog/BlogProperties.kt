package com.example.blog

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

// kotlin+ sprngbootでpropertiesを管理するためのベストプラクティス通りに実装
// https://spring.pleiades.io/guides/tutorials/spring-boot-kotlin/

@ConstructorBinding
@ConfigurationProperties("blog")
data class BlogProperties(var title: String, val banner: Banner) {
	data class Banner(val title: String? = null, val content: String)
}
