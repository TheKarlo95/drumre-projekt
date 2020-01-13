package hr.fer.drumre.rec.features.movies.favorites.adapter

internal enum class ItemView(val type: Int, val span: Int) {
    MOVIE(type = 0, span = 1),
    LOADING(type = 1, span = 2),
    ERROR(type = 2, span = 2);

    companion object {
        fun valueOf(type: Int): ItemView? = values().first { it.type == type }
    }
}
