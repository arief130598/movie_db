package com.arief.moviedb.repository.api

import com.arief.moviedb.api.ApiMovieDB
import com.arief.moviedb.model.Genres
import com.arief.moviedb.model.GenresResponse
import com.arief.moviedb.model.Movies
import com.arief.moviedb.model.MoviesResponse
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class ApiMovieDBRepoTest {
    private lateinit var apiMovieDB: ApiMovieDB
    private val validApi = "valid"
    private val invalidApi = "invalid"
    private lateinit var apiMovieDBRepo: ApiMovieDBRepo

    private val genresData = listOf(
        Genres(1, "Action"),
        Genres(2, "Horror")
    )
    private val successResourceGenres = Response.success(GenresResponse(genresData))
    private val errorResponseGenres = "{ Unauthorized }".toResponseBody("application/json".toMediaTypeOrNull())
    private val errorResourceGenres = Response.error<GenresResponse>(401, errorResponseGenres)

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

    private val successResourceMovies = Response.success(MoviesResponse(1, moviesData, 50, 20))
    private val errorResponseMovies = "{ Unauthorized }".toResponseBody("application/json".toMediaTypeOrNull())
    private val errorResourceMovies = Response.error<MoviesResponse>(401, errorResponseMovies)

    @Before
    fun setup(){
        apiMovieDB = mock()
        runBlocking {
            whenever(apiMovieDB.getGenres(validApi)).thenReturn(successResourceGenres)
            whenever(apiMovieDB.getGenres(invalidApi)).thenReturn(errorResourceGenres)

            whenever(apiMovieDB.getPopular(validApi, 1)).thenReturn(successResourceMovies)
            whenever(apiMovieDB.getPopular(invalidApi, 1)).thenReturn(errorResourceMovies)

            whenever(apiMovieDB.getNowPlaying(validApi, 1)).thenReturn(successResourceMovies)
            whenever(apiMovieDB.getNowPlaying(invalidApi, 1)).thenReturn(errorResourceMovies)

            whenever(apiMovieDB.getUpcoming(validApi, 1)).thenReturn(successResourceMovies)
            whenever(apiMovieDB.getUpcoming(invalidApi, 1)).thenReturn(errorResourceMovies)

            whenever(apiMovieDB.getSearch(validApi,"Terr", 1)).thenReturn(successResourceMovies)
            whenever(apiMovieDB.getSearch(invalidApi, "Terr",1)).thenReturn(errorResourceMovies)

            whenever(apiMovieDB.getSimilar(1, validApi, 1)).thenReturn(successResourceMovies)
            whenever(apiMovieDB.getSimilar(1, invalidApi, 1)).thenReturn(errorResourceMovies)
        }
        apiMovieDBRepo = ApiMovieDBRepo(apiMovieDB)
    }

    @Test
    fun `test getGenres when API is valid then genresData is returned`() = runBlocking{
        assertEquals(successResourceGenres, apiMovieDBRepo.getGenres(validApi))
    }

    @Test
    fun `test getGenres when API is invalid then throw Error Response`() = runBlocking{
        assertEquals(errorResourceGenres, apiMovieDBRepo.getGenres(invalidApi))
    }

    @Test
    fun `test getPopular when API is valid then genresData is returned`() = runBlocking{
        assertEquals(successResourceMovies, apiMovieDBRepo.getPopular(validApi, 1))
    }

    @Test
    fun `test getPopular when API is invalid then throw Error Response`() = runBlocking{
        assertEquals(errorResourceMovies, apiMovieDBRepo.getPopular(invalidApi, 1))
    }

    @Test
    fun `test getNowPlaying when API is valid then genresData is returned`() = runBlocking{
        assertEquals(successResourceMovies, apiMovieDBRepo.getNowPlaying(validApi, 1))
    }

    @Test
    fun `test getNowPlaying when API is invalid then throw Error Response`() = runBlocking{
        assertEquals(errorResourceMovies, apiMovieDBRepo.getNowPlaying(invalidApi, 1))
    }

    @Test
    fun `test getUpcoming when API is valid then genresData is returned`() = runBlocking{
        assertEquals(successResourceMovies, apiMovieDBRepo.getUpcoming(validApi, 1))
    }

    @Test
    fun `test getUpcoming when API is invalid then throw Error Response`() = runBlocking{
        assertEquals(errorResourceMovies, apiMovieDBRepo.getUpcoming(invalidApi, 1))
    }

    @Test
    fun `test getSearch when API is valid then genresData is returned`() = runBlocking{
        assertEquals(successResourceMovies, apiMovieDBRepo.getSearch(validApi,"Terr", 1))
    }

    @Test
    fun `test getSearch when API is invalid then throw Error Response`() = runBlocking{
        assertEquals(errorResourceMovies, apiMovieDBRepo.getSearch(invalidApi, "Terr",1))
    }

    @Test
    fun `test getSimilar when API is valid then genresData is returned`() = runBlocking{
        assertEquals(successResourceMovies, apiMovieDBRepo.getSimilar(1, validApi, 1))
    }

    @Test
    fun `test getSimilar when API is invalid then throw Error Response`() = runBlocking{
        assertEquals(errorResourceMovies, apiMovieDBRepo.getSimilar(1, invalidApi, 1))
    }
}