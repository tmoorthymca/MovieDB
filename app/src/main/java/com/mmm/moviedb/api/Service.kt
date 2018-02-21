package com.mmm.moviedb.api

import android.graphics.ColorSpace.Model
import com.mmm.moviedb.base.Config
import com.mmm.moviedb.model.Details
import com.mmm.moviedb.model.Movie
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Query
import javax.inject.Inject



/**
 * Created by Thirumoorthy on 2/19/2018.
 */
class Service @Inject constructor(restApi: RestApi) {
    private val restApi: RestApi
    init {
        this.restApi = restApi
    }

    fun getSearchResult(language:String="en-US", query:String, page:Int=1, includeAdult:String="false"): Observable<Movie>{
        return restApi.getMovieSearchResult(language=language,query = query,page = page,includeAdult = includeAdult)
    }

    fun getNowPlayingMovie(language:String="en-US",page:Int = 1):Single<Movie>{
        return restApi.getNowPlayingMovie(language=language,page = page)
    }

    fun getPopularMovie(language:String="en-US",page:Int = 1):Single<Movie>{
        return restApi.getPopularMovie(language=language,page = page)
    }

    fun getTopMovie(language:String="en-US", page:Int = 1):Single<Movie>{
        return restApi.getTopMovie(language=language,page = page)
    }

    fun getUpcomingMovie(language:String="en-US",page:Int = 1):Single<Movie>{
        return restApi.getUpcomingMovie(language=language,page = page)
    }

    fun getMovieDetails(movieId:Int,language:String="en-US"):Single<Details>{
        return restApi.getMovieDetails(movieId,language = language)
    }
}