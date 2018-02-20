package com.mmm.moviedb.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mmm.moviedb.R
import com.mmm.moviedb.model.Result

import kotlinx.android.synthetic.main.movie_item_view.view.*

/**
 * Created by Thirumoorthy on 2/19/2018.
 */
class MovieListAdapter: RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    var movieResult:List<Result> = ArrayList<Result>()

    fun addData(movieResult:List<Result>){
        this.movieResult = movieResult
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val value = movieResult.get(position)
            holder.bind(value)
    }

    override fun getItemCount() = 10//movieResult.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.movie_item_view, parent,false))
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(result: Result){
            itemView.voteCount.text = "${result.voteCount}"
        }
    }
}