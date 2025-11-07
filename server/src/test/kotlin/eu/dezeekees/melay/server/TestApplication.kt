package eu.dezeekees.melay.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.data.r2dbc.autoconfigure.DataR2dbcRepositoriesAutoConfiguration
import org.springframework.boot.r2dbc.autoconfigure.R2dbcAutoConfiguration

@SpringBootApplication(
    exclude = [
        R2dbcAutoConfiguration::class,
        DataR2dbcRepositoriesAutoConfiguration::class,
    ]
)
class TestApplication