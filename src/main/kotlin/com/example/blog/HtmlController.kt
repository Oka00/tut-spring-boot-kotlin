package com.example.blog

import org.springframework.http.HttpStatus.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException


// primary コンストラクタ
// primaryコンストラクタは 単一のコンストラクタなので
// Autowiredが自動で適応される
// ArticleRepositoryがDIコンテナから取得される
//
// ArticleRepositoryのBEAN定義どこでやってるのか見つからないが
// どうやらCrudRepositoryを使うと(勝手にBEAN定義やってくれるので)Autowiredできるようになるっぽい
// https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html
@Controller
	class HtmlController(private val repository: ArticleRepository,
					 private val properties: BlogProperties) {
	// (はじめにここから)localhost:8080にアクセスした際
	//
	//
	// return "blog"  →
	//    spring MVCがviewResolverのメソッドをよびだし
	//   　blog.mustacheを表示する
	//  model オブジェクトに色々値を格納することで blog.mustacheにいろいろ値を書くニオウすることができる
	//  ほかにも@ModelAttributeを使用する方法もある 参考本 243にのっている

	// (余談 リダイレクトする際や,　その際にパラメータを渡す 、　リダイレクト先を動的に決める手法は)
	//  参考本 240 241にのっている
	@GetMapping("/")
	fun blog(model: Model): String {
		model["title"] = properties.title
		model["banner"] = properties.banner
		model["articles"] = repository.findAllByOrderByAddedAtDesc().map { it.render() }
		return "blog"
	}
	//  blog.mustacheを表示した際
	//   リンクは "/article/{{slug}}"の形式になる
	// 　　それを開くとここにマッピングされる
	@GetMapping("/article/{slug}")
	fun article(@PathVariable slug: String, model: Model): String {
		val article = repository
				.findBySlug(slug)
				?.render()
				?: throw ResponseStatusException(NOT_FOUND, "This article does not exist")
		model["title"] = article.title
		model["article"] = article
		return "article"
	}

	fun Article.render() = RenderedArticle(
			slug,
			title,
			headline,
			content,
			author,
			addedAt.format()
	)

	data class RenderedArticle(
			val slug: String,
			val title: String,
			val headline: String,
			val content: String,
			val author: User,
			val addedAt: String)

}
