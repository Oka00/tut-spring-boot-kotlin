package com.example.blog

import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.*

// kotlinではutilクラスを使う代わりに
// ここのコードのようにkotlinの拡張機能を介してそのような機能を提供する
// https://spring.pleiades.io/guides/tutorials/spring-boot-kotlin/
fun LocalDateTime.format() = this.format(englishDateFormatter)

// このコード内だけで使う変数
private val daysLookup = (1..31).associate { it.toLong() to getOrdinal(it) }

// このコード内だけで使う変数
private val englishDateFormatter = DateTimeFormatterBuilder()
		.appendPattern("yyyy-MM-dd")
		.appendLiteral(" ")
		.appendText(ChronoField.DAY_OF_MONTH, daysLookup)
		.appendLiteral(" ")
		.appendPattern("yyyy")
		.toFormatter(Locale.ENGLISH)

private fun getOrdinal(n: Int) = when {
	n in 11..13 -> "${n}th"
	n % 10 == 1 -> "${n}st"
	n % 10 == 2 -> "${n}nd"
	n % 10 == 3 -> "${n}rd"
	else -> "${n}th"
}

fun String.toSlug() = toLowerCase()
		.replace("\n", " ")
		.replace("[^a-z\\d\\s]".toRegex(), " ")
		.split(" ")
		.joinToString("-")
		.replace("-+".toRegex(), "-")
