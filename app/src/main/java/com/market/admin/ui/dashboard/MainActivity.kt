package com.market.admin.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import com.market.admin.R
import com.market.admin.ui.dashboard.fragment.HomeFragment
import com.market.admin.ui.dashboard.fragment.NotificationFragment
import com.market.admin.ui.dashboard.fragment.ProfileFragment
import com.market.admin.ui.dashboard.fragment.StoreFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                setFragmentSupport(HomeFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_stores -> {
                setFragmentSupport(StoreFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                setFragmentSupport(NotificationFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                setFragmentSupport(ProfileFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFragmentSupport(HomeFragment.newInstance())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun setFragmentSupport(f: Fragment) {
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.main_fram, f)
            .commit()

    }

    /*Camera crop */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragments = supportFragmentManager.fragments
        for (f in fragments) {
            if (f is ProfileFragment) {
                f.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

}
