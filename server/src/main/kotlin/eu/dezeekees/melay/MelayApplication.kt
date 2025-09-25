package eu.dezeekees.melay

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MelayApplication

fun main(args: Array<String>) {
	runApplication<MelayApplication>(*args)
}
