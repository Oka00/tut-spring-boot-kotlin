package com.example.blog

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BlogConfiguration {
	// primary コンストラクタ
	// primaryコンストラクタは 単一のコンストラクタなので
	// Autowiredが自動で適応される
	// UserRepository, ArticleRepositoryがDIコンテナから取得される
	//
	// UserRepository, ArticleRepositoryのBEAN定義どこでやってるのか見つからないが
	// どうやらCrudRepositoryを使うと(勝手にBEAN定義やってくれるので)Autowiredできるようになるっぽい
	// https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html

	// Application Runner で applicationが起動してすぐ実行する内容を書ける
	// しかしここの文法は謎
	// ApplicationRunner { } つまり class { } なんて文法あったっけ？
	@Bean
    fun databaseInitializer(userRepository: UserRepository,
							articleRepository: ArticleRepository) = ApplicationRunner {

        val smaldini = userRepository.save(User("smaldini", "Stéphane", "Maldini"))
        articleRepository.save(Article(
				title = "Reactor Bismuth is out",
				headline = "Lorem ipsum",
				content = "dolor sit amet",
				author = smaldini
		))
        articleRepository.save(Article(
				title = "Reactor Aluminium has landed",
				headline = "Lorem ipsum",
				content = "dolor sit amet",
				author = smaldini
		))
    }
}
