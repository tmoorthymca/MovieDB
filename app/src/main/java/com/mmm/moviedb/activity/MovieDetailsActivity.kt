package com.mmm.moviedb.activity

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import com.mmm.moviedb.R
import com.mmm.moviedb.base.Config
import com.mmm.moviedb.base.ProgressDialog
import com.mmm.moviedb.model.Details
import com.mmm.moviedb.model.Result
import com.mmm.moviedb.presenter.DetailsPresenter
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.content_movie_details.*

class MovieDetailsActivity : BaseActivity() {
    lateinit var result:Result
    lateinit var progressDialog:ProgressDialog
    lateinit var presenter:DetailsPresenter

    override fun setLayout() = R.layout.activity_movie_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        result = intent.extras.getSerializable("DERAILS") as Result
        progressDialog = ProgressDialog(this,android.R.style.Theme_Translucent_NoTitleBar)
        presenter = DetailsPresenter(this)

        imageView.setImageURI(Uri.parse(Config.IMAGE_BASE_URL+result.backdropPath),null)

        progressDialog.show()
        presenter.compositeDisposable.add(presenter.getMovieDetails(result.id)
                .subscribe({
                    progressDialog.dismiss()
                    setDetailsUI(it)
                },
                        {
                            progressDialog.dismiss()
                            Config.retrofitErrorResponse(this,it)
                        }))
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    @SuppressLint("SetTextI18n")
    private fun setDetailsUI(details: Details){
        overViewTxt.text = details.overview
        voteTxt.text = "${details.voteCount}"
        if(!details.productionCompanies.isEmpty()) {
            val name = StringBuilder()
            details.productionCompanies.forEach({
                name.append("${it.name}, ")
            })
            productionCompaniesTxt.text = "production : ${name.toString()}"
            budgetTxt.text = "Budget : ${details.budget}"
            if(details.revenue>0){
                revenueTxt.text = "Budget : ${details.revenue}"
            }

            releaseDateTxt.text = "Release Date : ${details.releaseDate}"
            if(details.adult.equals("true")){
                adultTxt.text = "Adult : Yes"
            }else{
                adultTxt.text = "Adult : Yes"
            }
        }
    }
}
