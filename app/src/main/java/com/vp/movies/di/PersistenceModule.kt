package com.vp.movies.di

import android.app.Application
import com.vp.movies.persistence.FavouritesRepository
import com.vp.movies.persistence.FavouritesRepositoryPrefsImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule() {

    @Singleton
    @Provides
    fun providesFavourites(application: Application): FavouritesRepository = FavouritesRepositoryPrefsImpl(application)
}