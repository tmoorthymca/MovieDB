package com.mithran.tm_base_library

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_base_activity.*

class BAseActivityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_activity)

        serverBtn.setOnClickListener {
            RetrofitManager.apiCallFromServer(this,Service.IExampleNetwork::class.java)
                    .getUpcomingMovie(RetrofitManager.API_KEY,"En",1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({},{})
        }

        servercacheBtn.setOnClickListener {
            RetrofitManager.apiCallFromCache(this,Service.IExampleNetwork::class.java)
                    .getUpcomingMovie(RetrofitManager.API_KEY,"En",1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({},{})
        }

        cacheBtn.setOnClickListener {
            RetrofitManager.apiCallFromServerOrCache(this,Service.IExampleNetwork::class.java)
                    .getUpcomingMovie(RetrofitManager.API_KEY,"En",1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({},{})
        }
    }
}
