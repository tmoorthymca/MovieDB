package com.mmm.moviedb.adapter

import android.content.Context
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.view.LayoutInflater
import com.mmm.moviedb.R
import com.mmm.moviedb.base.Config
import com.mmm.moviedb.model.Result
import java.util.ArrayList
import kotlinx.android.synthetic.main.search_result_view.view.*

/**
 * Created by Mirudhula on 2/20/2018.
 */
class SearchListAdapter(context: Context,results:List<Result>) : BaseAdapter() {

    var inflater: LayoutInflater
    var results:List<Result> = ArrayList<Result>()

    init {
        this.results = results
        inflater = LayoutInflater.from(context)
    }

    override fun getView(position: Int,convertView : View?, parent: ViewGroup?): View {
        val view: View?
        if (convertView == null) {
            view = inflater.inflate(R.layout.search_result_view, parent, false)
        } else {
            view = convertView
        }

        val result = results.get(position)
        view!!.movieName.text = result.originalTitle
        view.movieImageView.setImageURI(Uri.parse(Config.IMAGE_BASE_URL+result.posterPath),null)
        return view!!
    }

    override fun getItem(position: Int): Any {
        return results.get(position)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return results.size
    }
}