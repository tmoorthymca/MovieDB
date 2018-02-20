package com.mmm.moviedb.api

import com.mmm.moviedb.base.Config
import com.mmm.moviedb.model.Details
import com.mmm.moviedb.model.Movie
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Thirumoorthy on 2/19/2018.
 */
interface RestApi {

    @GET("search/movie")
    fun getMovieSearchResult(@Query("api_key")apiKey:String= Config.API_KEY, @Query("language")language:String, @Query("query")query:String, @Query("page")page:Int, @Query("include_adult")includeAdult:String):Observable<Movie>

    @GET("movie/now_playing")
    fun getNowPlayingMovie(@Query("api_key")apiKey:String= Config.API_KEY, @Query("language")language:String, @Query("page")page:Int):Single<Movie>

    @GET("movie/popular")
    fun getPopularMovie(@Query("api_key")apiKey:String= Config.API_KEY, @Query("language")language:String, @Query("page")page:Int):Single<Movie>

    @GET("movie/top_rated")
    fun getTopMovie(@Query("api_key")apiKey:String= Config.API_KEY, @Query("language")language:String, @Query("page")page:Int):Single<Movie>

    @GET("movie/upcoming")
    fun getUpcomingMovie(@Query("api_key")apiKey:String= Config.API_KEY, @Query("language")language:String, @Query("page")page:Int):Single<Movie>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId:Int, @Query("api_key")apiKey:String= Config.API_KEY, @Query("language")language:String):Single<Details>
}