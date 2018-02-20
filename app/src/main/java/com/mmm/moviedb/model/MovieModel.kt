package com.mmm.moviedb.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Thirumoorthy on 2/19/2018.
 */
data class Movie(@SerializedName("page")
                  @Expose val page:Int,
                    @SerializedName("total_results")
                    @Expose val totalResults:Int,
                    @SerializedName("total_pages")
                    @Expose val totalPages:Int,
                    @SerializedName("results")
                    @Expose val results: List<Result>)



data class Result(@SerializedName("vote_count")
                  @Expose val voteCount: Int,
                  @SerializedName("id")
                    @Expose val id: Int,
                    @SerializedName("video")
                    @Expose val video: Boolean,
                    @SerializedName("vote_average")
                    @Expose val voteAverage: Int,
                    @SerializedName("title")
                    @Expose val title: String,
                    @SerializedName("popularity")
                    @Expose val popularity: Double,
                    @SerializedName("poster_path")
                    @Expose val posterPath: Any,
                    @SerializedName("original_language")
                    @Expose val originalLanguage: String,
                    @SerializedName("original_title")
                    @Expose val originalTitle: String,
                    @SerializedName("genre_ids")
                    @Expose val genreIds: List<Int>,
                    @SerializedName("backdrop_path")
                    @Expose val backdropPath: Any,
                    @SerializedName("adult")
                    @Expose val adult: Boolean,
                    @SerializedName("overview")
                    @Expose val overview: String,
                    @SerializedName("release_date")
                    @Expose val releaseDate: String)
