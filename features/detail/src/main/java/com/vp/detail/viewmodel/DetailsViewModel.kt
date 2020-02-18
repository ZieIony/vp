package com.vp.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vp.detail.DetailActivity
import com.vp.detail.model.MovieDetail
import com.vp.detail.service.DetailService
import com.vp.movies.persistence.FavouritesRepository
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.security.auth.callback.Callback

class DetailsViewModel @Inject constructor(
        private val detailService: DetailService,
        private val favouritesRepository: FavouritesRepository
) : ViewModel() {

    private val favourite: MutableLiveData<FavouriteState> = MutableLiveData()

    private val details: MutableLiveData<MovieDetail> = MutableLiveData()
    private val title: MutableLiveData<String> = MutableLiveData()
    private val loadingState: MutableLiveData<LoadingState> = MutableLiveData()

    fun favourite(): LiveData<FavouriteState> = favourite

    fun title(): LiveData<String> = title

    fun details(): LiveData<MovieDetail> = details

    fun state(): LiveData<LoadingState> = loadingState

    fun fetchDetails() {
        loadingState.value = LoadingState.IN_PROGRESS
        favourite.value = FavouriteState.IN_PROGRESS
        val movieId = DetailActivity.queryProvider.getMovieId()
        detailService.getMovie(movieId).enqueue(object : Callback, retrofit2.Callback<MovieDetail> {
            override fun onResponse(call: Call<MovieDetail>?, response: Response<MovieDetail>?) {
                details.postValue(response?.body())

                response?.body()?.title?.let {
                    title.postValue(it)
                }

                if (favouritesRepository.isFavourite(movieId)) {
                    favourite.value = FavouriteState.FAVOURITE
                } else {
                    favourite.value = FavouriteState.NOT_FAVOURITE
                }

                loadingState.value = LoadingState.LOADED
            }

            override fun onFailure(call: Call<MovieDetail>?, t: Throwable?) {
                details.postValue(null)
                loadingState.value = LoadingState.ERROR
            }
        })
    }

    fun toggleFavourite() {
        // TODO: make me async some day
        val movieId = DetailActivity.queryProvider.getMovieId()
        favourite.value = FavouriteState.IN_PROGRESS
        if (favouritesRepository.isFavourite(movieId)) {
            favouritesRepository.removeFromFavourites(movieId)
            favourite.value = FavouriteState.NOT_FAVOURITE
        } else {
            favouritesRepository.addToFavourites(movieId)
            favourite.value = FavouriteState.FAVOURITE
        }
    }

    enum class LoadingState {
        IN_PROGRESS, LOADED, ERROR
    }

    enum class FavouriteState {
        FAVOURITE, IN_PROGRESS, NOT_FAVOURITE
    }
}