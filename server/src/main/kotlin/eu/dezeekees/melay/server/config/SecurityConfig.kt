package eu.dezeekees.melay.server.config

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.security.JwtAuthenticationConverter
import eu.dezeekees.melay.server.security.JwtAuthenticationManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {

        val jwtFilter = AuthenticationWebFilter(JwtAuthenticationManager()).apply {
            setServerAuthenticationConverter(JwtAuthenticationConverter())
            setSecurityContextRepository(WebSessionServerSecurityContextRepository())
            setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.anyExchange())
        }

        return http
            .csrf{ csrf -> csrf.disable() }
            .formLogin { formLogin -> formLogin.disable() }
            .authorizeExchange { exchanges ->
                exchanges
                    .pathMatchers(
                        "/swagger-ui/*",
                        "/v3/api-docs",
                        "/v3/api-docs/swagger-config"
                    ).permitAll()
                    .pathMatchers(HttpMethod.POST, Routes.Api.User.NAME).permitAll()
                    .pathMatchers(HttpMethod.POST, Routes.Api.Auth.LOGIN).permitAll()
                    .anyExchange().authenticated()
            }
            .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}