package com.mmm.moviedb.activity

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.mmm.moviedb.R
import com.mmm.moviedb.base.Config
import com.mmm.moviedb.base.ProgressDialog
import com.mmm.moviedb.model.Result
import com.mmm.moviedb.presenter.DetailsPresenter
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.content_movie_details.*
import kotlinx.android.synthetic.main.movie_item_view.view.*

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

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        imageView.setImageURI(Uri.parse(Config.IMAGE_BASE_URL+result.backdropPath),null)

        progressDialog.show()
        presenter.compositeDisposable.add(presenter.getMovieDetails(result.id)
                .subscribe({
                    progressDialog.dismiss()
                    overViewTxt.text = it.overview
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
}
