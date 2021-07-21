package com.example.blog

import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

// このコードによって
// HTTP API を公開することができている

// HTTP requestがでいるようになる

// ±  curl http://localhost:8080/api/article/
//[{"title":"Reactor Aluminium has landed","headline":"Lorem ipsum","content":"dolor sit amet","author":{"login":"smaldini","firstname":"Stéphane","lastname":"Maldini","description":null,"id":1},"slug":"reactor-aluminium-has-landed","addedAt":"2021-06-27T19:43:50.031697","id":3},{"title":"Reactor Bismuth is out","headline":"Lorem ipsum","content":"dolor sit amet","author":{"login":"smaldini","firstname":"Stéphane","lastname":"Maldini","description":null,"id":1},"slug":"reactor-bismuth-is-out","addedAt":"2021-06-27T19:43:50.02233","id":2}]%

// ±  curl http://localhost:8080/api/article/reactor-aluminium-has-landed
//{"title":"Reactor Aluminium has landed","headline":"Lorem ipsum","content":"dolor sit amet","author":{"login":"smaldini","firstname":"Stéphane","lastname":"Maldini","description":null,"id":1},"slug":"reactor-aluminium-has-landed","addedAt":"2021-06-27T19:43:50.031697","id":3}%
@RestController
@RequestMapping("/api/article")
class ArticleController(private val repository: ArticleRepository) {

	// fun XXX = 値
	// でkotlinではfun XXX { return 値}やってるのとおなじになる
	@GetMapping("/")
	fun findAll() = repository.findAllByOrderByAddedAtDesc()

	@GetMapping("/{slug}")
	fun findOne(@PathVariable slug: String) =
			repository.findBySlug(slug) ?: throw ResponseStatusException(NOT_FOUND, "This article does not exist")

}

@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository) {

	@GetMapping("/")
	fun findAll() = repository.findAll()

	@GetMapping("/{login}")
	fun findOne(@PathVariable login: String) = repository.findByLogin(login) ?: throw ResponseStatusException(NOT_FOUND, "This user does not exist")
}
