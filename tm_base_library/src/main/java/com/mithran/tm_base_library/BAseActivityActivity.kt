package com.mithran.tm_base_library

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BAseActivityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_activity)

        Service(RetrofitManager(this)).getCachedDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({},{})
    }
}
