package com.mmm.moviedb.presenter

import android.content.Context
import com.mmm.moviedb.api.Service
import com.mmm.moviedb.base.MovieApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Thirumoorthy on 2/20/2018.
 */
class DetailsPresenter(context: Context) : BasePresenter() {
    @Inject
    lateinit var service: Service

    init {
        MovieApplication.getApp(context).getDataComponent().inject(this)
    }

    fun getMovieDetails(movieId:Int) = service.getMovieDetails(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}