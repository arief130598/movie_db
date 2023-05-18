package com.arief.moviedb.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.arief.moviedb.BuildConfig
import com.arief.moviedb.utils.MainCoroutineRule
import com.arief.moviedb.model.Movies
import com.arief.moviedb.model.MoviesResponse
import com.arief.moviedb.repository.api.ApiMovieDBRepo
import com.arief.moviedb.ui.search.SearchViewModel
import com.arief.moviedb.core.utils.NetworkHelper
import com.arief.moviedb.core.utils.Resource
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private lateinit var apiMovieDBRepo: ApiMovieDBRepo
    private lateinit var networkHelper: NetworkHelper
    private lateinit var moviesObserver: Observer<Resource<List<Movies>>>

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
    private val moviesData2 = mutableListOf(
        Movies(
            false, "/y5Z0WesTjvn59jP6yo459eUsbli.jpg", listOf(27,53), 663714, "en",
            "Terrifier 3", "After being resurrected by a sinister entity, Art the Clown returns to " +
                    "Miles County where he must hunt down and destroy a teenage girl and her younger brother on Halloween night.  " +
                    "As the body count rises, the siblings fight to stay alive while uncovering the true nature of Art's evil intent.",
            9049.191F,"/y5Z0WesTjvn59jP6yo459eUsbli.jpg", "2022-10-06", "Terrifier 2", 7.4F,
            246
        ),
        Movies(
            false, "/y5Z0WesTjvn59jP6yo459eUsbli.jpg", listOf(27,53), 663715, "en",
            "Terrifier 4", "After being resurrected by a sinister entity, Art the Clown returns to " +
                    "Miles County where he must hunt down and destroy a teenage girl and her younger brother on Halloween night.  " +
                    "As the body count rises, the siblings fight to stay alive while uncovering the true nature of Art's evil intent.",
            9049.191F,"/y5Z0WesTjvn59jP6yo459eUsbli.jpg", "2022-10-06", "Terrifier 2", 7.4F,
            246
        )
    )
    private val moviesData3 = mutableListOf(
        Movies(
            false, "/y5Z0WesTjvn59jP6yo459eUsbli.jpg", listOf(27,53), 663716, "en",
            "Black 1", "After being resurrected by a sinister entity, Art the Clown returns to " +
                    "Miles County where he must hunt down and destroy a teenage girl and her younger brother on Halloween night.  " +
                    "As the body count rises, the siblings fight to stay alive while uncovering the true nature of Art's evil intent.",
            9049.191F,"/y5Z0WesTjvn59jP6yo459eUsbli.jpg", "2022-10-06", "Terrifier 2", 7.4F,
            246
        ),
        Movies(
            false, "/y5Z0WesTjvn59jP6yo459eUsbli.jpg", listOf(27,53), 663717, "en",
            "Black 2", "After being resurrected by a sinister entity, Art the Clown returns to " +
                    "Miles County where he must hunt down and destroy a teenage girl and her younger brother on Halloween night.  " +
                    "As the body count rises, the siblings fight to stay alive while uncovering the true nature of Art's evil intent.",
            9049.191F,"/y5Z0WesTjvn59jP6yo459eUsbli.jpg", "2022-10-06", "Terrifier 2", 7.4F,
            246
        )
    )

    private val result1 = Response.success(MoviesResponse(1, moviesData, 50, 20))
    private val result2 = Response.success(MoviesResponse(2, moviesData2, 50, 20))
    private val result3 = Response.success(MoviesResponse(1, moviesData3, 50, 20))

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
        networkHelper = mock()
        apiMovieDBRepo = mock()
        moviesObserver = mock()

        runBlocking {
            whenever(apiMovieDBRepo.getSearch(BuildConfig.API_KEY, "Terr", 1)).thenReturn(
                result1
            )
            whenever(apiMovieDBRepo.getSearch(BuildConfig.API_KEY, "Terr", 2)).thenReturn(
                result2
            )
            whenever(apiMovieDBRepo.getSearch(BuildConfig.API_KEY, "Black", 1)).thenReturn(
                result3
            )
            whenever(networkHelper.isNetworkConnected()).thenReturn(true)
        }
        viewModel = SearchViewModel(apiMovieDBRepo, networkHelper)
    }

    @Test
    fun `when getSearch called then observe with live data`() = runBlocking {
        viewModel.movies.observeForever(moviesObserver)

        viewModel.getMovies("Terr")
        verify(moviesObserver).onChanged(Resource.loading(null))
        verify(apiMovieDBRepo).getSearch(BuildConfig.API_KEY, "Terr", 1)
        verify(moviesObserver).onChanged(Resource.success(result1.body()!!.results))
        assertEquals(moviesData.size, viewModel.listLoadedMovies.size)
    }

    @Test
    fun `when getSearch is called twice then load more data`() = runBlocking {
        viewModel.movies.observeForever(moviesObserver)

        viewModel.getMovies("Terr")
        verify(moviesObserver).onChanged(Resource.loading(null))
        verify(apiMovieDBRepo).getSearch(BuildConfig.API_KEY, "Terr", 1)
        verify(moviesObserver).onChanged(Resource.success(result1.body()!!.results))
        assertEquals(moviesData.size, viewModel.listLoadedMovies.size)

        viewModel.getMovies("Terr")
        verify(moviesObserver, atLeastOnce()).onChanged(Resource.loading(null))
        verify(apiMovieDBRepo).getSearch(BuildConfig.API_KEY, "Terr", 2)
        verify(moviesObserver, atLeastOnce()).onChanged(Resource.success(result2.body()!!.results))
        val newData = moviesData + moviesData2
        assertEquals(newData.size, viewModel.listLoadedMovies.size)
    }

    @Test
    fun `when getSearch is called twice with diff query then replace data`() = runBlocking {
        viewModel.movies.observeForever(moviesObserver)

        viewModel.getMovies("Terr")
        verify(moviesObserver).onChanged(Resource.loading(null))
        verify(apiMovieDBRepo).getSearch(BuildConfig.API_KEY, "Terr", 1)
        verify(moviesObserver).onChanged(Resource.success(result1.body()!!.results))
        assertEquals(moviesData.size, viewModel.listLoadedMovies.size)

        viewModel.page = 0
        viewModel.listLoadedMovies.clear()

        viewModel.getMovies("Black")
        verify(moviesObserver, atLeastOnce()).onChanged(Resource.loading(null))
        verify(apiMovieDBRepo).getSearch(BuildConfig.API_KEY, "Black", 1)
        verify(moviesObserver, atLeastOnce()).onChanged(Resource.success(result3.body()!!.results))
        assertEquals(moviesData3.size, viewModel.listLoadedMovies.size)
    }
}