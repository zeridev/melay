package eu.dezeekees.melay.util

import eu.dezeekees.melay.mapper.BaseMapper
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

// Map a Flux of entities to a Flux of response DTOs
fun <E, R> Flux<E>.mapToResponse(mapper: BaseMapper<E, *, R>): Flux<R> =
    this.map { mapper.toResponse(it) }

// Map a Mono of entity to a Mono of response DTO
fun <E, R> Mono<E>.mapToResponse(mapper: BaseMapper<E, *, R>): Mono<R> =
    this.map { mapper.toResponse(it) }
