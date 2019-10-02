package com.market.admin.ui.dashboard.fragment

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.market.admin.R
import com.market.admin.model.ResponseModel
import com.market.admin.model.ResultData
import com.market.admin.mvvm.VerifyViewModel
import com.market.admin.network.NetworkUtil
import com.market.admin.network.RestClient
import com.market.admin.ui.dashboard.MainActivity
import com.market.admin.utils.showSnackBar
import com.market.admin.utils.toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.adapter_vender_list.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {


    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }


    private var rec: RecyclerView? = null
    private lateinit var list: ArrayList<ResultData>
    private lateinit var mActivity: MainActivity
    private val instanceViewModel by lazy { VerifyViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = (activity!! as MainActivity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home, container, false)
        initObservers()
        initView(v)
        return v
    }

    private fun initView(v: View?) {
        rec = v!!.recyclerView
        rec!!.layoutManager = LinearLayoutManager(mActivity)

        reCall()
    }

    private fun reCall(){
        setProgress(true)
        instanceViewModel.getVenderListAPI()

    }

    private fun initObservers() {
        instanceViewModel.requestUpdateData.observe(this, Observer {
            setProgress(false)
            if (it!!.status!!) {
                parseData(it)
            }
        })
        instanceViewModel.errorMess.observe(this, Observer {
            setProgress(false)
            mActivity.showSnackBar(it!!)
        })
    }

    private fun parseData(it: ResponseModel) {
        list = it.result as ArrayList<ResultData>

        val mLangAdapter = MenuAdapter(list, object : MenuAdapter.ItemClickListener {
            override fun onItemClicked(repos: ResultData, isCheck: Int) {
                    onVerifySubmit(repos, isCheck)
            }
        })
        rec!!.adapter = mLangAdapter
        mLangAdapter.notifyDataSetChanged()
    }

    @SuppressLint("CheckResult")
    private fun onVerifySubmit(repos: ResultData, is_verify: Int) {
        setProgress(true)
        RestClient.webServices().getVerify(repos.uid,repos.venderId,is_verify)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setProgress(false)
                if (it.status!!) {
                    mActivity.toast(it.message!!)
                }
            },
                {
                    setProgress(false)
                    val mess = NetworkUtil.isHttpStatusCode(it)
                    Log.d("Admin Submit Verify", "Error=>$mess")
                }
            )

    }


    class MenuAdapter(var list: ArrayList<ResultData>, var listener: ItemClickListener) :
        RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
        interface ItemClickListener {
            fun onItemClicked(repos: ResultData, isCheck: Int)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_vender_list, parent, false)
            return ViewHolder(v)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItems(list[position])
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            @SuppressLint("SetTextI18n")
            val context = itemView.context

            @SuppressLint("SetTextI18n")
            fun bindItems(model: ResultData) {

                //itemView.img.setImageResource(model.img)

                itemView.item_title.text = model.fullname
                itemView.tv_date.text = model.createAt
                itemView.tv_category.text = model.category

                if (model.isVerify == 1) {
                    itemView.rb_pending.isChecked=true
                    itemView.rb_approve.isChecked=false
                    itemView.rb_reject.isChecked=false

                    itemView.tv_status.text = "Pending"
                    itemView.tv_status.setTextColor(Color.BLUE)
                }
                if (model.isVerify == 2) {
                    itemView.rb_pending.isChecked=false
                    itemView.rb_approve.isChecked=true
                    itemView.rb_reject.isChecked=false
                    itemView.tv_status.setTextColor(Color.GREEN)
                    itemView.tv_status.text = "Verified"
                }
                if (model.isVerify == 3) {
                    itemView.rb_pending.isChecked=false
                    itemView.rb_approve.isChecked=false
                    itemView.rb_reject.isChecked=true
                    itemView.tv_status.setTextColor(Color.RED)
                    itemView.tv_status.text = "Reject"
                }

                itemView.rg.setOnCheckedChangeListener { _, checkedId ->
                    when (checkedId) {
                        R.id.rb_pending -> {
                            itemView.tv_status.text = "Pending"
                            itemView.tv_status.setTextColor(Color.BLUE)
                            listener.onItemClicked(model, 1)

                        }
                        R.id.rb_approve -> {
                            itemView.tv_status.text = "Approve"
                            itemView.tv_status.setTextColor(Color.GREEN)
                            listener.onItemClicked(model, 2)

                        }
                        R.id.rb_reject -> {
                            itemView.tv_status.text = "Reject"
                            itemView.tv_status.setTextColor(Color.RED)
                            listener.onItemClicked(model, 3)

                        }
                    }
                }
            }
        }

    }


    private fun setProgress(flag: Boolean) {
        if (flag)
            mActivity.showProgress()
        else mActivity.hideProgress()
    }

}
