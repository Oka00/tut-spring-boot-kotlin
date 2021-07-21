package com.example.blog

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

// サーバーをランダムなポートで起動させる
//https://qiita.com/NagaokaKenichi/items/7236a68e0cdb77a97479

// @Autowired val restTemplate: TestRestTemplate
// これいつBean登録されてるかわからんかったが
// @SpringBootTestをつかうとBean登録されるらしい
// timesで他のエンジニアにもそれであってること教えていただいた
//https://spring.pleiades.io/spring-boot/docs/current/api/org/springframework/boot/test/web/client/TestRestTemplate.html
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests(@Autowired val restTemplate: TestRestTemplate) {

	@BeforeAll
	fun setup() {
		println(">> Setup")
	}

	@Test
	fun `Assert blog page title, content and status code`() {
		println(">> Assert blog page title, content and status code")
		// HTTPステータスコード、レスポンスヘッダ、レスポンスボディを取得する必要がある場合は、getForEntityメソッドを使用する。
		// https://terasolunaorg.github.io/guideline/5.1.0.RELEASE/ja/ArchitectureInDetail/RestClient.html
		//  <> はジェネリティクス
		// https://www.sejuku.net/blog/22699
		val entity = restTemplate.getForEntity<String>("/")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.body).contains("<h1>Blog</h1>", "Reactor")
	}

	@Test
	fun `Assert article page title, content and status code`() {
		println(">> Assert article page title, content and status code")
		val title = "Reactor Aluminium has landed"
		val entity = restTemplate.getForEntity<String>("/article/${title.toSlug()}")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.body).contains(title, "Lorem ipsum", "dolor sit amet")
	}

	@AfterAll
	fun teardown() {
		println(">> Tear down")
	}

}