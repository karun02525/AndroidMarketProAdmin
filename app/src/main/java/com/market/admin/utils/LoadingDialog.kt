package com.market.admin.utils

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import com.market.admin.R
import kotlinx.android.synthetic.main.activity_loading_dialog.view.*


class LoadingDialog {
    private var mProgress: Dialog? = null
    private var progressDrawable: Drawable? = null
    fun dismissLoader() {
        try {
            if (mProgress != null) {
                mProgress!!.cancel()
                mProgress = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getProgressDrawableColors(context: Context): IntArray {
        val colors = IntArray(4)
        colors[0] = ContextCompat.getColor(context, R.color.colorPrimary)
        colors[1] = ContextCompat.getColor(context, R.color.green_light)
        colors[2] = ContextCompat.getColor(context, R.color.yellow)
        colors[3] = ContextCompat.getColor(context, R.color.purple)
        return colors
    }

    fun showLoader(con: Context) {
        progressDrawable = ChromeFloatingProgressDialog.Builder(con)
            .colors(getProgressDrawableColors(con))
            .build()
        if (mProgress != null) {
            mProgress!!.cancel()
        }
        mProgress = Dialog(con)
        mProgress!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mProgress!!.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        val wmlp = mProgress!!.window!!.attributes

        wmlp.gravity = Gravity.CENTER
        mProgress!!.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mProgress!!.setCancelable(false)
        val view = LayoutInflater.from(con).inflate(R.layout.activity_loading_dialog, null)
        mProgress!!.setContentView(view)
        val progressBar = view.google_progress

        val bounds = progressBar.indeterminateDrawable.bounds
        progressBar.indeterminateDrawable = progressDrawable
        progressBar.indeterminateDrawable.bounds = bounds
        mProgress!!.show()
    }

    companion object {

        internal var mInstance: LoadingDialog? = null

        fun initLoader() {
            if (mInstance == null)
                mInstance = LoadingDialog()
        }

        val loader: LoadingDialog
            get() {
                if (mInstance != null)
                    return mInstance!!
                else {
                    mInstance = LoadingDialog()
                    return mInstance!!
                }
            }
    }
}
