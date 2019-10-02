package com.market.admin.ui.dashboard.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.market.admin.R

class StoreFragment  : Fragment() {

    companion object {
        fun newInstance(): StoreFragment {
            return StoreFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v= inflater.inflate(R.layout.fragment_store, container, false)
        return v
    }

}
