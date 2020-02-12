package com.example.myarchitecture.view.mainActivity

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myarchitecture.R
import com.example.myarchitecture.databinding.ResultProfileBinding
import com.example.myarchitecture.model.notificationModels.NotificationModel
import com.example.myarchitecture.shared.data.networking.NetworkState
import com.example.myarchitecture.view.BaseActivity
import com.example.myarchitecture.view.adapters.NotificationAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var mBinding: ResultProfileBinding
    private val viewModel: MainViewModel by lazy { createViewModel(MainViewModel::class.java) }
    private var mAdapter:NotificationAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBinding = ResultProfileBinding.inflate(layoutInflater)

//        viewModel.getLiveData()?.observe(this, Observer<List<NotificationModel>> {
//            main_text.text = it?.size.toString()
//        })
//        viewModel.api()

        initSubscribers()

        initAdapter()
    }

    private fun initSubscribers() {
        viewModel.getNotificationLiveData()?.observe(this, Observer<PagedList<NotificationModel>> {
            mAdapter?.submitList(it)
        })

        viewModel.getPaginationLiveData()?.observe(this, Observer<NetworkState> {
            mAdapter?.setNetworkState(it)
        })

        viewModel.mErrorLiveData.observe(this, Observer<NetworkState> {
            Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
        })
    }

    private fun initAdapter() {
        mAdapter = NotificationAdapter()
        main_recycler_view.layoutManager = LinearLayoutManager(this)
        main_recycler_view.adapter = mAdapter
    }
}