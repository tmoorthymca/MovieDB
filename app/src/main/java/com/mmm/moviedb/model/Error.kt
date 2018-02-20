package com.mmm.moviedb.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by Thirumoorthy on 2/19/2018.
 */
data class Error(
    @SerializedName("status_message")
    @Expose val statusMessage: String ,
    @SerializedName("success")
    @Expose val success: Boolean ,
    @SerializedName("status_code")
    @Expose val statusCode: Int)