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
    private var mAdapter: NotificationAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        mViewModel.getNotifications()

        initAdapter()

        initSubscribers()

        return mBinding.root
    }

    private fun initSubscribers() {
        requestSubscriber(mViewModel, mBinding.homeStateLayout)

        mViewModel.getNotificationLiveData()?.observe(super.getViewLifecycleOwner(), Observer {
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

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel.getLiveData().removeObservers(viewLifecycleOwner)
    }
}