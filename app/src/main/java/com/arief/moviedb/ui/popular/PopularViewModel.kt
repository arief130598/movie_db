package com.arief.moviedb.ui.popular

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
import com.arief.moviedb.utils.Resource
import kotlinx.coroutines.*

class PopularViewModel(private val moviesRepo: MoviesRepo,
                       private val genresRepo: GenresRepo,
                       private val apiMovieDBRepo: ApiMovieDBRepo,
                       private val networkHelper: NetworkHelper) : ViewModel() {

    private val ioScope = CoroutineScope(Job() + Dispatchers.IO)

    private val _movies = MutableLiveData<Resource<List<Movies>>>()
    val movies: LiveData<Resource<List<Movies>>>
        get() = _movies

    private val _favorite = MutableLiveData<List<Movies>>()
    val favorite: LiveData<List<Movies>>
        get() = _favorite

    private val _genres = MutableLiveData<List<Genres>>()
    val genres: LiveData<List<Genres>>
        get() = _genres

    private var page = 0
    var lastPositionAdapter = 0
    var listLoadedMovies: MutableList<Movies> = mutableListOf()

    init {
        getGenres()
    }

    fun getFavorite(){
        ioScope.launch {
            _favorite.postValue(moviesRepo.getList())
        }
    }

    private fun getGenres(){
        ioScope.launch {
            apiMovieDBRepo.getGenres(BuildConfig.API_KEY).let {
                if (it.isSuccessful && it.body() != null) {
                    if(it.body()!!.genres.isNotEmpty()){
                        withContext(Dispatchers.IO){
                            genresRepo.insertList(it.body()!!.genres)
                        }
                        _genres.postValue(it.body()!!.genres)
                    }else{
                        withContext(Dispatchers.IO){
                            val genres = genresRepo.getList()
                            _genres.postValue(genres)
                        }
                    }
                } else {
                    withContext(Dispatchers.IO){
                        val genres = genresRepo.getList()
                        _genres.postValue(genres)
                    }
                }
            }
        }.invokeOnCompletion {
            getMovies()
        }
    }

    fun getMovies(){
        page += 1
        ioScope.launch {
            _movies.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                apiMovieDBRepo.getPopular(BuildConfig.API_KEY, page).let {
                    if (it.isSuccessful) {
                        if(it.body() != null) {
                            _movies.postValue(Resource.success(it.body()!!.results))
                            listLoadedMovies.addAll(it.body()!!.results)
                        }else{
                            _movies.postValue(Resource.success(listOf()))
                        }
                    } else _movies.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _movies.postValue(Resource.error("No internet connection", null))
        }
    }

    fun setLastPosition(position: Int) {
        lastPositionAdapter = position
    }

    fun insertFavorite(item: Movies) {
        ioScope.launch {
            moviesRepo.insertSingle(item)
        }
    }

    fun deleteFavorite(item: Movies) {
        ioScope.launch {
            moviesRepo.deleteSingle(item.id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        ioScope.cancel()
    }
}