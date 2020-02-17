package com.example.myarchitecture.view.mainActivity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myarchitecture.R
import com.example.myarchitecture.databinding.MainBinding
import com.example.myarchitecture.model.notificationModels.NotificationModel
import com.example.myarchitecture.shared.data.networking.RequestState
import com.example.myarchitecture.view.adapters.NotificationAdapter
import com.example.myarchitecture.view.baseView.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var mBinding: MainBinding
    private val viewModel: MainViewModel by lazy { createViewModel(MainViewModel::class.java) }
    private var mAdapter: NotificationAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

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

        viewModel.mErrorLiveData.observe(this, Observer<RequestState> {
            if (!it.isRootLoading)
                mAdapter?.setNetworkState(it)
            else {
                when (it.status) {
                    RequestState.Status.EMPTY ->
                        mBinding.mainStateLayout.showEmpty()
                    RequestState.Status.NETWORK_ERROR ->
                        mBinding.mainStateLayout.showNetworkError()
                    RequestState.Status.SERVER_ERROR ->
                        mBinding.mainStateLayout.showServerError()
                    RequestState.Status.API_ERROR ->
                        mBinding.mainStateLayout.showServerError()
                    RequestState.Status.LOADING ->
                        mBinding.mainStateLayout.showLoading()
                    RequestState.Status.SUCCESS ->
                        mBinding.mainStateLayout.showContent()
                }
            }
        })
    }

    private fun initAdapter() {
        mAdapter = NotificationAdapter()
        main_recycler_view.layoutManager = LinearLayoutManager(this)
        main_recycler_view.adapter = mAdapter
    }
}