package com.example.myarchitecture.view.mainActivity.fragments.homeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myarchitecture.databinding.HomeBinding
import com.example.myarchitecture.model.notificationModels.NotificationModel
import com.example.myarchitecture.shared.data.networking.RequestState
import com.example.myarchitecture.view.adapters.NotificationAdapter
import com.example.myarchitecture.view.baseView.BaseFragment

class HomeFragment : BaseFragment() {

    private lateinit var mBinding: HomeBinding
    private val mViewModel: HomeViewModel by lazy { mActivity.createViewModel(HomeViewModel::class.java) }
    private var mAdapter: NotificationAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = HomeBinding.inflate(inflater, container, false)

        mViewModel.getNotifications()

        initSubscribers()

        initAdapter()

        return mBinding.root
    }

    private fun initSubscribers() {
        mViewModel.getNotificationLiveData()?.observe(this, Observer<PagedList<NotificationModel>> {
            mAdapter?.submitList(it)
        })

        mViewModel.mErrorLiveData.observe(this, Observer<RequestState> {
            if (!it.isRootLoading)
                mAdapter?.setNetworkState(it)
            else {
                when (it.status) {
                    RequestState.Status.EMPTY ->
                        mBinding.homeStateLayout.showEmpty()
                    RequestState.Status.NETWORK_ERROR ->
                        mBinding.homeStateLayout.showNetworkError()
                    RequestState.Status.SERVER_ERROR ->
                        mBinding.homeStateLayout.showServerError()
                    RequestState.Status.API_ERROR ->
                        mBinding.homeStateLayout.showServerError()
                    RequestState.Status.LOADING ->
                        mBinding.homeStateLayout.showLoading()
                    RequestState.Status.SUCCESS ->
                        mBinding.homeStateLayout.showContent()
                }
            }
        })
    }

    private fun initAdapter() {
        mAdapter = NotificationAdapter()
        mBinding.mainRecyclerView.layoutManager = LinearLayoutManager(mActivity)
        mBinding.mainRecyclerView.adapter = mAdapter
    }
}