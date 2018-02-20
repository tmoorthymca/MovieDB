package com.mmm.moviedb.base

import android.app.Application
import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco
import com.mmm.moviedb.component.DaggerNetworkComponent
import com.mmm.moviedb.component.NetworkComponent
import com.mmm.moviedb.module.NetworkModule


/**
 * Created by Mirudhula on 2/19/2018.
 */
class MovieApplication : Application() {

    lateinit var networkComponent: NetworkComponent

    companion object {
        fun getApp(context: Context):MovieApplication{
            return (context.applicationContext) as MovieApplication
        }
    }
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        initDataComponent()
        networkComponent.inject(this)
    }

    private fun initDataComponent() {
        networkComponent = DaggerNetworkComponent.builder()
                .networkModule(NetworkModule(Config.BASE_URL))
                .build()
    }

    fun getDataComponent(): NetworkComponent {
        return networkComponent
    }
}