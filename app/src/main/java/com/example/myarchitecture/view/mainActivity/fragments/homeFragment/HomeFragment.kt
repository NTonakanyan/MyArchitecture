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
    private val mViewModel: HomeViewModel by lazy { createViewModel(HomeViewModel::class.java) }
    private var mAdapter: NotificationAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = HomeBinding.inflate(inflater, container, false)

        mViewModel.getNotifications()

        initAdapter()

        initSubscribers()

        return mBinding.root
    }

    private fun initSubscribers() {
        requestSubscriber(mViewModel, mBinding.homeStateLayout)

        mViewModel.getNotificationLiveData()?.observe(viewLifecycleOwner, Observer<PagedList<NotificationModel>> {
            mAdapter?.submitList(it)
        })
    }

    private fun initAdapter() {
        mAdapter = NotificationAdapter()
        mBinding.mainRecyclerView.layoutManager = LinearLayoutManager(mActivity)
        mBinding.mainRecyclerView.adapter = mAdapter
    }

    override fun requestState(requestState: RequestState) {
        if (!requestState.isRootLoading)
            mAdapter?.setNetworkState(requestState)
    }
}