package com.vp.movies.persistence


interface FavouritesRepository {
    fun addToFavourites(movieId: String)

    fun removeFromFavourites(movieId: String)

    fun isFavourite(movieId: String): Boolean

    fun getFavourites(): Set<String>
}