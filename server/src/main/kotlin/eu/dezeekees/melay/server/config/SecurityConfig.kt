package eu.dezeekees.melay.server.config

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.service.impl.JwtAuthenticationManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    private val jwtAuthenticationManager: JwtAuthenticationManager,
) {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .authorizeExchange { exchanges ->
                exchanges
                    .pathMatchers(
                        "/login",
                        "/swagger-ui/*",
                        "/v3/api-docs",
                        "/v3/api-docs/swagger-config"
                    ).permitAll()
                    .pathMatchers(HttpMethod.POST, "/api/user").permitAll()
                    .anyExchange().authenticated()
            }
            .csrf { csrf ->
                csrf.disable()
            }
            .formLogin { formLogin ->
                formLogin.disable()
            }
            .authenticationManager(jwtAuthenticationManager)
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}