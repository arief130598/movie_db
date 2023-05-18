package com.arief.moviedb.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.arief.moviedb.BuildConfig
import com.arief.moviedb.utils.MainCoroutineRule
import com.arief.moviedb.model.Genres
import com.arief.moviedb.model.GenresResponse
import com.arief.moviedb.model.Movies
import com.arief.moviedb.repository.api.ApiMovieDBRepo
import com.arief.moviedb.repository.db.GenresRepo
import com.arief.moviedb.repository.db.MoviesRepo
import com.arief.moviedb.ui.movie.MovieViewModel
import com.arief.moviedb.core.utils.NetworkHelper
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel
    private lateinit var moviesRepo: MoviesRepo
    private lateinit var genresRepo: GenresRepo
    private lateinit var apiMovieDBRepo: ApiMovieDBRepo
    private lateinit var networkHelper: NetworkHelper
    private lateinit var genresObserver: Observer<List<Genres>>
    private lateinit var favoriteObserver: Observer<List<Movies>>

    private val singleMovies = Movies(
        false, "/y5Z0WesTjvn59jP6yo459eUsbli.jpg", listOf(27,53), 777777, "en",
        "Terrifier 3", "After being resurrected by a sinister entity, Art the Clown returns to " +
                "Miles County where he must hunt down and destroy a teenage girl and her younger brother on Halloween night.  " +
                "As the body count rises, the siblings fight to stay alive while uncovering the true nature of Art's evil intent.",
        9049.191F,"/y5Z0WesTjvn59jP6yo459eUsbli.jpg", "2022-10-06", "Terrifier 2", 7.4F,
        246
    )
    private val moviesData = mutableListOf(
        Movies(
            false, "/y5Z0WesTjvn59jP6yo459eUsbli.jpg", listOf(27,53), 663712, "en",
            "Terrifier 1", "After being resurrected by a sinister entity, Art the Clown returns to " +
                    "Miles County where he must hunt down and destroy a teenage girl and her younger brother on Halloween night.  " +
                    "As the body count rises, the siblings fight to stay alive while uncovering the true nature of Art's evil intent.",
            9049.191F,"/y5Z0WesTjvn59jP6yo459eUsbli.jpg", "2022-10-06", "Terrifier 2", 7.4F,
            246
        ),
        Movies(
            false, "/y5Z0WesTjvn59jP6yo459eUsbli.jpg", listOf(27,53), 663713, "en",
            "Terrifier 2", "After being resurrected by a sinister entity, Art the Clown returns to " +
                    "Miles County where he must hunt down and destroy a teenage girl and her younger brother on Halloween night.  " +
                    "As the body count rises, the siblings fight to stay alive while uncovering the true nature of Art's evil intent.",
            9049.191F,"/y5Z0WesTjvn59jP6yo459eUsbli.jpg", "2022-10-06", "Terrifier 2", 7.4F,
            246
        )

    )
    private val genresData = listOf(
        Genres(1, "Action"),
        Genres(2, "Horror")
    )
    private val successCase = true
    private val successResourceGenres = Response.success(GenresResponse(genresData))
    private val errorResponseBody = "{ Unauthorized }".toResponseBody("application/json".toMediaTypeOrNull())
    private val errorResourceGenres = Response.error<GenresResponse>(401, errorResponseBody)

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Rule
    @JvmField
    val instantTaskExecutorRules = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup(){
        moviesRepo = mock()
        genresRepo = mock()
        networkHelper = mock()
        apiMovieDBRepo = mock()
        genresObserver = mock()
        favoriteObserver = mock()

        runBlocking {
            if(successCase){
                whenever(apiMovieDBRepo.getGenres(BuildConfig.API_KEY)).thenReturn(successResourceGenres)
            }else{
                whenever(apiMovieDBRepo.getGenres(BuildConfig.API_KEY)).thenReturn(errorResourceGenres)
            }
            whenever(networkHelper.isNetworkConnected()).thenReturn(true)
            whenever(genresRepo.getList()).thenReturn(genresData)
            whenever(moviesRepo.getList()).thenReturn(moviesData)
            whenever(moviesRepo.insertSingle(singleMovies)).thenReturn(1)
            whenever(moviesRepo.deleteSingle(moviesData[0].id)).thenReturn(1)
        }
        viewModel = MovieViewModel(moviesRepo, genresRepo, apiMovieDBRepo, networkHelper)
    }

    @Test
    fun `when getGenres is called then observe with live data when api is success or error`() = runBlocking {
        viewModel.genres.observeForever(genresObserver)
        // No Need to call function cause running in counstructor (init)
        if(successCase) {
            verify(apiMovieDBRepo).getGenres(BuildConfig.API_KEY)
            verify(genresRepo).insertList(successResourceGenres.body()!!.genres)
            verify(genresObserver).onChanged(successResourceGenres.body()!!.genres)
        }else{
            verify(apiMovieDBRepo).getGenres(BuildConfig.API_KEY)
            verify(genresRepo).getList()
            verify(genresObserver).onChanged(genresData)
        }
    }

    @Test
    fun `when getFavorite is called get from DB and observe data`() = runBlocking {
        viewModel.favorite.observeForever(favoriteObserver)
        // No Need to call function cause running in constructor (init)
        verify(moviesRepo).getList()
        verify(favoriteObserver).onChanged(moviesData)
    }

    @Test
    fun `when deleteFavorite delete from DB then update observe and favoriteSize is minus one`() = runBlocking {
        viewModel.favorite.observeForever(favoriteObserver)
        // No Need to call function cause running in constructor (init)
        val sizeBefore = viewModel.favorite.value?.size
        val initialData = moviesData[0]

        viewModel.deleteFavorite(initialData)
        verify(moviesRepo).deleteSingle(initialData.id)

        assertEquals(sizeBefore!! -1, viewModel.favorite.value!!.size)
        assertEquals(false, (viewModel.favorite.value!!.any { it.id == initialData.id }))
    }

    @Test
    fun `when insertFavorite save to DB then update observe and favoriteSize is plus one`() = runBlocking {
        viewModel.favorite.observeForever(favoriteObserver)
        // No Need to call function cause running in constructor (init)
        val sizeBefore = viewModel.favorite.value?.size

        viewModel.insertFavorite(singleMovies)
        verify(moviesRepo).insertSingle(singleMovies)

        assertEquals(sizeBefore!! +1, viewModel.favorite.value!!.size)
        assertEquals(true, (viewModel.favorite.value!!.any { it.id == singleMovies.id }))
    }
}