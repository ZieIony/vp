package com.vp.movies.persistence

import android.content.Context
import android.content.SharedPreferences
import java.util.*


// TODO: use SQL
class FavouritesRepositoryPrefsImpl : FavouritesRepository {
    private val preferences: SharedPreferences

    constructor(context: Context) {
        preferences = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
    }

    override fun addToFavourites(movieId: String) {
        val favs = preferences.getStringSet(FAVOURITES, HashSet())!!
        favs.add(movieId)
        preferences.edit().putStringSet(FAVOURITES, favs).commit()
    }

    override fun removeFromFavourites(movieId: String) {
        val favs = preferences.getStringSet(FAVOURITES, HashSet())!!
        favs.remove(movieId)
        preferences.edit().putStringSet(FAVOURITES, favs).commit()
    }

    override fun isFavourite(movieId: String): Boolean {
        val favs = preferences.getStringSet(FAVOURITES, HashSet())!!
        return favs.contains(movieId)
    }

    override fun getFavourites(): Set<String> {
        return preferences.getStringSet(FAVOURITES, HashSet())!!
    }

    companion object {
        const val PREFS_FILE = "favourites"
        const val FAVOURITES = "favourites"
    }
}