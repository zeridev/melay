package eu.dezeekees.melay.server.controller

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.dto.request.LoginRequest
import eu.dezeekees.melay.server.dto.response.TokenResponse
import eu.dezeekees.melay.server.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class AuthController(
    private val authService: AuthService
) {
    @PostMapping(Routes.Api.Auth.LOGIN)
    fun login(@RequestBody request: LoginRequest): Mono<TokenResponse> = authService.login(request)
}