package eu.dezeekees.melay.server

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
class MelayApplication

fun main(args: Array<String>) {
	runApplication<MelayApplication>(*args)
}
