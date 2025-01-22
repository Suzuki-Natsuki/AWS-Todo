package com.dig.toodles

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ToodlesApplication

fun main(args: Array<String>) {
	runApplication<ToodlesApplication>(*args)
}
