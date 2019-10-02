package com.market.admin.ui.dashboard


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.market.admin.utils.LoadingDialog
import com.market.admin.utils.leftToRight
import com.market.admin.utils.rightToLeft


abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leftToRight()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        rightToLeft()
        return true
    }


    override fun onBackPressed() {
        super.onBackPressed()
        rightToLeft()
    }


    fun showProgress() {
        LoadingDialog.loader.showLoader(this@BaseActivity)
    }

    fun hideProgress() {
        LoadingDialog.loader.dismissLoader()
    }
}
