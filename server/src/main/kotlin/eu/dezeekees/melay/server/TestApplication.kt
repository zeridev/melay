package eu.dezeekees.melay.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.data.r2dbc.autoconfigure.DataR2dbcRepositoriesAutoConfiguration
import org.springframework.boot.r2dbc.autoconfigure.R2dbcAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(
    exclude = [
        R2dbcAutoConfiguration::class,
        DataR2dbcRepositoriesAutoConfiguration::class,
    ]
)
class TestApplication

fun main(args: Array<String>) {
    runApplication<TestApplication>(*args)
}
