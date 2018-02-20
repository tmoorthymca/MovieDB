package com.mmm.moviedb.base

import android.content.Context
import android.widget.Toast
import retrofit2.HttpException
import com.google.gson.GsonBuilder
import com.mmm.moviedb.BuildConfig
import com.mmm.moviedb.model.Error
import java.lang.Exception


/**
 * Created by Thirumoorthy on 2/19/2018.
 */
class Config {
    companion object {
        val API_KEY = "7085aa44d49e3fca2114530e37f88055"
        val BASE_URL = "https://api.themoviedb.org/3/"
        val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"


        fun retrofitErrorResponse(context: Context,throwable:Throwable){
            if(throwable is HttpException) {
                val errorBody = throwable.response().body().toString()
                try {
                    val gson = GsonBuilder().create()
                    val error = gson.fromJson(errorBody, Error::class.java)
                    Toast.makeText(context, error.statusMessage, Toast.LENGTH_SHORT).show()
                }catch (e:Exception){
                    if(BuildConfig.DEBUG){
                        e.printStackTrace()
                    }
                }
            }else{
                Toast.makeText(context,throwable.message,Toast.LENGTH_SHORT).show()
            }
        }
    }
}