package com.mmm.moviedb.base

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import com.mmm.moviedb.R

/**
 * Created by Mirudhula on 2/20/2018.
 */
class ProgressDialog(context: Context,style:Int) : Dialog(context,style) {
    init {
        setContentView(R.layout.progress_dialog_view)
    }
}