package com.mmm.moviedb.presenter

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Mirudhula on 2/20/2018.
 */
open class BasePresenter {

    var compositeDisposable = CompositeDisposable()

    fun onDestroy() {
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }
}