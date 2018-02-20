package com.mmm.moviedb.component

import com.mmm.moviedb.api.Service
import com.mmm.moviedb.base.MovieApplication
import com.mmm.moviedb.module.NetworkModule
import com.mmm.moviedb.presenter.DetailsPresenter
import com.mmm.moviedb.presenter.HomePresenter
import dagger.Component
import javax.inject.Singleton


/**
 * Created by Mirudhula on 2/19/2018.
 */
@Singleton
@Component(modules = arrayOf(NetworkModule::class))
interface NetworkComponent {

    fun inject(app: MovieApplication)

    fun inject(service: Service)

    fun inject(homePresenter: HomePresenter)

    fun inject(detailsPresenter: DetailsPresenter)
}