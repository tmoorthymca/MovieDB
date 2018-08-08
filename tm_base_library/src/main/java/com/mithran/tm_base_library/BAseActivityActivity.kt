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
            Service(RetrofitManager(this)).getServerDetails()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({},{})
        }

        servercacheBtn.setOnClickListener {
            Service(RetrofitManager(this)).getServerCachedDetails()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({},{})
        }

        cacheBtn.setOnClickListener {
            Service(RetrofitManager(this)).getCachedDetails()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({},{})
        }
    }
}
