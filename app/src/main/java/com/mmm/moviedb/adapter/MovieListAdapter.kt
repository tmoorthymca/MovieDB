package com.mmm.moviedb.adapter

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mmm.moviedb.R
import com.mmm.moviedb.activity.MovieDetailsActivity
import com.mmm.moviedb.base.Config
import com.mmm.moviedb.model.Result

import kotlinx.android.synthetic.main.movie_item_view.view.*

/**
 * Created by Thirumoorthy on 2/19/2018.
 */
class MovieListAdapter: RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.movie_item_view, parent,false))
    }

    var movieResult:List<Result> = ArrayList<Result>()

    fun addData(movieResult:List<Result>){
        this.movieResult = movieResult
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val value = movieResult.get(position)
            holder.bind(value)
    }

    override fun getItemCount() = movieResult.size

    /*override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.movie_item_view, parent,false))
    }*/

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(result: Result){
            itemView.movieImage.setImageURI(Uri.parse(Config.IMAGE_BASE_URL+result.posterPath),null)
            itemView.setOnClickListener {
                val intent = Intent(itemView.context,MovieDetailsActivity::class.java)
                intent.putExtra("DERAILS",result)
                itemView.context.startActivity(intent)
            }
        }
    }
}