package com.mmm.moviedb.activity

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.mmm.moviedb.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import android.text.TextUtils
import android.speech.RecognizerIntent
import com.miguelcatalan.materialsearchview.MaterialSearchView
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.mmm.moviedb.BuildConfig
import com.mmm.moviedb.adapter.MovieListAdapter
import com.mmm.moviedb.base.Config
import com.mmm.moviedb.base.ProgressDialog
import com.mmm.moviedb.model.Movie
import com.mmm.moviedb.presenter.HomePresenter
import kotlinx.android.synthetic.main.base_tool_bar.*
import kotlinx.android.synthetic.main.content_home.*

class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun setLayout()=R.layout.activity_home
    private lateinit var presenter: HomePresenter
    private lateinit var adapter:MovieListAdapter
    private lateinit var progressDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        progressDialog = ProgressDialog(this,android.R.style.Theme_Translucent_NoTitleBar)

        movieList.setHasFixedSize(true)
        movieList.layoutManager = GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        adapter = MovieListAdapter()
        movieList.adapter = adapter

        presenter = HomePresenter(this)

        searchView.setVoiceSearch(false)
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.length>=3) {
                    presenter.getSearchResult(newText)
                            .subscribe({
                                adapter.addData(it.results)
                                if (BuildConfig.DEBUG) {
                                    Log.v("@@@@", it.toString())
                                }
                            },
                                    {
                                        if (BuildConfig.DEBUG) {
                                            it.printStackTrace()
                                        }
                                    })
                }
                return true
            }
        })

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        nav_view.getMenu().getItem(0).setChecked(true);
        landingService()
    }

    override fun onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home, menu)

        val item = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)

        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_now_playing-> {
                if(!item.isChecked && item.itemId == R.id.nav_now_playing) {
                    landingService()
                }
            }
            R.id.nav_top -> {
                if(!item.isChecked && item.itemId == R.id.nav_top) {
                    progressDialog.show()
                    presenter.compositeDisposable.add(presenter.getTopMovies()
                            .subscribe({
                                setAdapterData(it)
                            }, {
                                progressDialog.dismiss()
                                Config.retrofitErrorResponse(this, it)
                            }))
                }
            }
            R.id.nav_popular-> {
                if(!item.isChecked && item.itemId == R.id.nav_popular) {
                    progressDialog.show()
                    presenter.compositeDisposable.add(presenter.getPopularMovies()
                            .subscribe({
                                setAdapterData(it)
                            }, {
                                progressDialog.dismiss()
                                Config.retrofitErrorResponse(this, it)
                            }))
                }
            }
            R.id.nav_upcoming -> {
                if(!item.isChecked && item.itemId == R.id.nav_upcoming) {
                    progressDialog.show()
                    presenter.compositeDisposable.add(presenter.getUpcomingMovies()
                            .subscribe({
                                setAdapterData(it)
                            }, {
                                progressDialog.dismiss()
                                Config.retrofitErrorResponse(this, it)
                            }))
                }
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun setAdapterData(movie:Movie){
        progressDialog.dismiss()
        val results = movie.results
        if(!results.isEmpty()){
            adapter.addData(results)
        }
    }

    fun landingService(){
        progressDialog.show()
        presenter.compositeDisposable.add(presenter.getNowPlayingMovies()
                .subscribe({
                    setAdapterData(it)
                },{
                    Config.retrofitErrorResponse(this,it)
                }))
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}
