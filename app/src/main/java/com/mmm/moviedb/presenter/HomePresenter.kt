package com.mmm.moviedb.presenter

import android.content.Context
import com.mmm.moviedb.api.Service
import com.mmm.moviedb.base.MovieApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Thirumoorthy on 2/19/2018.
 */
class HomePresenter(context: Context) :BasePresenter() {
    @Inject
    lateinit var service: Service

    init {
        MovieApplication.getApp(context).getDataComponent().inject(this)
    }

    fun getSearchResult(query:String) = service.getSearchResult(query=query)
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getNowPlayingMovies() = service.getNowPlayingMovie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    fun getTopMovies() = service.getTopMovie()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getPopularMovies() = service.getPopularMovie()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getUpcomingMovies() = service.getUpcomingMovie()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}