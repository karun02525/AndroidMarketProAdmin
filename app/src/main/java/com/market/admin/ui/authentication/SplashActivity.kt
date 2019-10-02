package com.market.admin.ui.authentication

import android.os.Bundle
import android.os.Handler
import com.market.admin.R
import com.market.admin.ui.dashboard.BaseActivity
import com.market.admin.ui.dashboard.MainActivity
import com.market.admin.utils.SharedPref
import com.market.admin.utils.startNewActivityFlag

class SplashActivity : BaseActivity() {


    private val sp by lazy { SharedPref.instance }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        showProgress()
        initOperation()
    }

    private fun initOperation() {
        Handler().postDelayed({
            startNewActivityFlag(MainActivity::class.java)
            if(sp.isLoginStatus !=0){
            //    startNewActivityFlag(MainActivity::class.java)
            }else {
                //startNewActivityFlag(LoginActivity::class.java)
            }
            hideProgress()
            try {
               // log("FCM Token=>: ", getDeviceToken()!!)
            } catch (e: Exception) {
            }
        },100)


    }


}
