package eu.dezeekees.melay.mapper


interface BaseMapper<E, Req, Res> {

    // Request DTO → Entity
    fun fromRequest(request: Req): E

    // Entity → Response DTO
    fun toResponse(entity: E): Res

    // Optional: convenience extension for reactive pipelines
    fun Iterable<E>.toResponseList(): List<Res> = this.map { toResponse(it) }
    fun List<E>.toResponseList(): List<Res> = this.map { toResponse(it) }
}
