package eu.dezeekees.melay.server.presentation.controller

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.logic.dto.request.CreateUserRequest
import eu.dezeekees.melay.server.logic.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping(Routes.Api.User.NAME)
class UserController(
    private val userService: UserService
) {
    @PostMapping
    fun create(@Valid @RequestBody request: CreateUserRequest) = userService.create(request)
        .then(Mono.just(ResponseEntity.ok()))

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String) = userService.getById(id)
}