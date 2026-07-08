package com.example.gamevault.model

sealed class LibraryItem(
    open val id: Long,
    open val title: String,
    open val genre: String,
    open val year: Int,
    open val rating: Int,
    open val description: String
) {
    abstract val typeName: String

    data class VideoGame(
        override val id: Long,
        override val title: String,
        override val genre: String,
        override val year: Int,
        override val rating: Int,
        override val description: String,
        val platform: String
    ) : LibraryItem(id, title, genre, year, rating, description) {
        override val typeName: String = "Video Game"
    }

    data class BoardGame(
        override val id: Long,
        override val title: String,
        override val genre: String,
        override val year: Int,
        override val rating: Int,
        override val description: String,
        val players: String
    ) : LibraryItem(id, title, genre, year, rating, description) {
        override val typeName: String = "Board Game"
    }
}
