package com.example.blog

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

// コンストラクタインジェクションする
// (1)UserRepository, ArticleRepositoryのBEAN定義はどこでやってるのか見つからないが
// どうやらCrudRepositoryを使うと(勝手にBEAN定義やってくれるので)Autowiredできるようになるっぽい
// https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html
//  (2)DataJpaTest によって TestEntityManagerのBEAN定義やっている
// https://spring.pleiades.io/spring-boot/docs/2.0.2.RELEASE/reference/html/boot-features-testing.html
@DataJpaTest
class RepositoriesTests @Autowired constructor(
		val entityManager: TestEntityManager,
		val userRepository: UserRepository,
		val articleRepository: ArticleRepository) {


	// TestEntityManager
	// persist :EntityがPersistenceContextに格納
	//find : PersistenceContextのEntityが返却される、PersistenceContextにない時はQueryを発行して、RDBよりマッピング対象のレコードを取得
	//flush: PersistenceContextのEntityをRDBに反映
	//だからflush入れなくても動くが、 RDBに反映させてテストしないと意味がないのでそのようにしている
	// http://terasolunaorg.github.io/guideline/5.7.0.RELEASE/ja/ArchitectureInDetail/DataAccessDetail/DataAccessJpa.html
	fun `When findByIdOrNull then return Article`() {
		val juergen = User("springjuergen", "Juergen", "Hoeller")
		entityManager.persist(juergen)
		val article = Article("Spring Framework 5.0 goes GA", "Dear Spring community ...", "Lorem ipsum", juergen)
		entityManager.persist(article)
		entityManager.flush()
		// !!は 任意の値をnull非許容型に変換し、値がnullの場合は例外をスローします。
		//	https://kotlinlang.org/docs/null-safety.html#the-operator
		//
		val found = articleRepository.findByIdOrNull(article.id!!)
		assertThat(found).isEqualTo(article)
	}

	@Test
	fun `When findByLogin then return User`() {
		val juergen = User("springjuergen", "Juergen", "Hoeller")
		entityManager.persist(juergen)
		entityManager.flush()
		val user = userRepository.findByLogin(juergen.login)
		assertThat(user).isEqualTo(juergen)
	}
}
