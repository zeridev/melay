package eu.dezeekees.melay.server.presentation.controller

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.presentation.dto.auth.LoginRequest
import eu.dezeekees.melay.server.presentation.dto.auth.TokenResponse
import eu.dezeekees.melay.server.logic.service.AuthService
import eu.dezeekees.melay.server.presentation.mapper.AuthMapper
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class AuthController(
    private val authService: AuthService
) {
    @PostMapping(Routes.Api.Auth.LOGIN)
    fun login(@Valid @RequestBody request: LoginRequest): Mono<TokenResponse> = authService.login(request.username, request.password)
        .map(AuthMapper::toTokenResponse)
}