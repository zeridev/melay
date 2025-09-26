package eu.dezeekees.melay

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MelayApplication

private val log = LoggerFactory.getLogger(MelayApplication::class.java)


fun main(args: Array<String>) {
	log.info("test")
	runApplication<MelayApplication>(*args)
}
