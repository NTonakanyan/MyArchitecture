package com.example.myarchitecture.view.mainActivity.fragments.homeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myarchitecture.databinding.FragmentHomeBinding
import com.example.myarchitecture.shared.data.networking.RequestState
import com.example.myarchitecture.view.adapters.NotificationAdapter
import com.example.myarchitecture.view.baseView.BaseFragment

class HomeFragment : BaseFragment() {

    private lateinit var mBinding: FragmentHomeBinding
    private val mViewModel by viewModels<HomeViewModel>()
    private val mAdapter: NotificationAdapter = NotificationAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.getNotifications()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)

        initAdapter()

        initSubscribers()

        initView()

        return mBinding.root
    }

    private fun initView() {
        mBinding.mainSwipeRefresh.setOnRefreshListener {
            mViewModel.getNotifications(false)
        }
    }

    private fun initSubscribers() {
        requestSubscriber(mViewModel, mBinding.homeStateLayout)

        mViewModel.getNotificationLiveData().observe(super.getViewLifecycleOwner(), Observer {
            if (mAdapter.itemCount == 0)
                mAdapter.submitList(it)
        })
    }

    private fun initAdapter() {
        mBinding.mainRecyclerView.layoutManager = LinearLayoutManager(mActivity)
        mBinding.mainRecyclerView.adapter = mAdapter
    }

    override fun requestState(requestState: RequestState) {
        if (!requestState.isMainRequest)
            mAdapter.setNetworkState(requestState)
    }
}