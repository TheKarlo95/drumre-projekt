package hr.fer.drumre.rec.core.mapper

interface Mapper<F, T> {

    suspend fun map(from: F): T
}
