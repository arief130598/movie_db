package com.arief.moviedb.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arief.moviedb.BuildConfig
import com.arief.moviedb.model.Genres
import com.arief.moviedb.model.Movies
import com.arief.moviedb.repository.api.ApiMovieDBRepo
import com.arief.moviedb.repository.db.GenresRepo
import com.arief.moviedb.repository.db.MoviesRepo
import com.arief.moviedb.utils.NetworkHelper
import kotlinx.coroutines.*

/**
 * Movie ViewModel
 *
 * This is activity viewmodel for MainActivity <br/>
 * Main function from this viewmodel is to synchronized favorites movies for each fragment
 *
 */
class MovieViewModel (private val moviesRepo: MoviesRepo,
                      private val genresRepo: GenresRepo,
                      private val apiMovieDBRepo: ApiMovieDBRepo,
                      private val networkHelper: NetworkHelper
) : ViewModel() {

    private val ioScope = CoroutineScope(Job() + Dispatchers.IO)

    private val _favorite = MutableLiveData<List<Movies>>()
    val favorite: LiveData<List<Movies>>
        get() = _favorite

    private val _genres = MutableLiveData<List<Genres>>()
    val genres: LiveData<List<Genres>>
        get() = _genres

    init {
        getGenres()
        getFavorite()
    }

    private fun getFavorite(){
        ioScope.launch {
            _favorite.postValue(moviesRepo.getList())
        }
    }

    /**
     * Call MovieDB API to get list of genres
     *
     */
    private fun getGenres(){
        ioScope.launch {
            if (networkHelper.isNetworkConnected()) {
                apiMovieDBRepo.getGenres(BuildConfig.API_KEY).let {
                    if (it.isSuccessful && it.body() != null) {
                        if (it.body()!!.genres.isNotEmpty()) {
                            withContext(Dispatchers.IO) {
                                genresRepo.insertList(it.body()!!.genres)
                            }
                            _genres.postValue(it.body()!!.genres)
                        } else {
                            withContext(Dispatchers.IO) {
                                val genres = genresRepo.getList()
                                _genres.postValue(genres)
                            }
                        }
                    } else {
                        withContext(Dispatchers.IO) {
                            val genres = genresRepo.getList()
                            _genres.postValue(genres)
                        }
                    }
                }
            }else{
                val genres = genresRepo.getList()
                _genres.postValue(genres)
            }
        }
    }

    /**
     * Insert favorite then invoke favorite data
     *
     * @param item
     */
    fun insertFavorite(item: Movies) {
        ioScope.launch {
            moviesRepo.insertSingle(item).let {
                if(it > 0) {
                    val favorite = _favorite.value as MutableList
                    favorite.add(item)
                    val favoriteList = favorite.toList()
                    _favorite.postValue(favoriteList)
                }
            }
        }
    }

    /**
     * Delete favorite and invoke favorite data
     *
     * @param item
     */
    fun deleteFavorite(item: Movies) {
        ioScope.launch {
            moviesRepo.deleteSingle(item.id).let {
                if(it > 0){
                    val favorite = _favorite.value as MutableList
                    favorite.remove(item)
                    val favoriteList = favorite.toList()
                    _favorite.postValue(favoriteList)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        ioScope.cancel()
    }
}