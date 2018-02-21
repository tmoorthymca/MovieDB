package com.mmm.moviedb.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mmm.moviedb.R
import java.lang.Exception

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Thread(Runnable {
            try {
                Thread.sleep(3000)
                startActivity(Intent(this,HomeActivity::class.java))
                finish()
            }catch (e:Exception){}
        }).start()
    }
}
