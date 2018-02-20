package com.mmm.moviedb.base

import android.graphics.Movie
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Thirumoorthy on 2/19/2018.
 */
interface RestApi {

    @GET("/search/movie")
    fun getMovieSearchResult(@Query("api_key")apiKey:String=Config.API_KEY, @Query("language")language:String="en-US", @Query("query")query:String,@Query("page")page:Int=1, @Query("include_adult")includeAdult:String="false"):Single<Movie>

    @GET("/movie/now_playing")
    fun getNowPlayingMovie(@Query("api_key")apiKey:String=Config.API_KEY, @Query("language")language:String="en-US",@Query("page")page:Int = 1):Single<Movie>

    @GET("/movie/popular")
    fun getPopularMovie(@Query("api_key")apiKey:String=Config.API_KEY, @Query("language")language:String="en-US",@Query("page")page:Int = 1):Single<Movie>

    @GET("/movie/top_rated")
    fun getTopMovie(@Query("api_key")apiKey:String=Config.API_KEY, @Query("language")language:String="en-US",@Query("page")page:Int = 1):Single<Movie>

    @GET("/movie/upcoming")
    fun getUpcomingMovie(@Query("api_key")apiKey:String=Config.API_KEY, @Query("language")language:String="en-US",@Query("page")page:Int = 1):Single<Movie>

    @GET("/movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId:String,@Query("api_key")apiKey:String=Config.API_KEY, @Query("language")language:String="en-US")
}