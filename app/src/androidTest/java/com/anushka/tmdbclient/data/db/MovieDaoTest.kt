package com.anushka.tmdbclient.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anushka.tmdbclient.data.model.movie.Movie
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: MovieDao
    private lateinit var database: TMDBDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TMDBDatabase::class.java
        ).build()
        dao = database.movieDao()
    }


    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun saveMovieTest(): Unit = runBlocking {
        val movies = listOf(
            Movie(1, "overview1", "posterPath1", "date1", "title1"),
            Movie(1, "overview2", "posterPath1", "date1", "title1"),
            Movie(1, "overview3", "posterPath1", "date1", "title1"),
            Movie(1, "overview4", "posterPath1", "date1", "title1")
        )

        dao.saveMovies(movies)
        val allMovies = dao.getMovies()
        Truth.assertThat(allMovies).isEqualTo(movies)
    }

    @Test
    fun deleteMovies():Unit = runBlocking  {
        val movies = listOf(
            Movie(1, "overview1", "posterPath1", "date1", "title1"),
            Movie(1, "overview2", "posterPath1", "date1", "title1"),
            Movie(1, "overview3", "posterPath1", "date1", "title1"),
            Movie(1, "overview4", "posterPath1", "date1", "title1")
        )

        dao.saveMovies(movies)
        dao.deleteAllMovies()
        val moviesResult = dao.getMovies()
        Truth.assertThat(moviesResult).isEmpty()
    }
}