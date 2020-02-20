package com.example.myarchitecture.view.mainActivity.fragments.peopleFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myarchitecture.databinding.AnnouncementBinding
import com.example.myarchitecture.model.announcementModels.AnnouncementModel
import com.example.myarchitecture.shared.data.networking.RequestState
import com.example.myarchitecture.view.adapters.AnnouncementAdapter
import com.example.myarchitecture.view.baseView.BaseFragment

class AnnouncementFragment : BaseFragment() {

    private lateinit var mBinding: AnnouncementBinding
    private var mAdapter: AnnouncementAdapter? = null
    private val mViewModel: AnnouncementViewModel by lazy { createViewModel(AnnouncementViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = AnnouncementBinding.inflate(inflater, container, false)

        mViewModel.getAnnouncements()

        initAdapter()

        initSubscribers()

        return mBinding.root
    }

    private fun initSubscribers() {
        requestSubscriber(mViewModel, mBinding.announcementStateLayout)

        mViewModel.getAnnouncementsLiveData()?.observe(viewLifecycleOwner, Observer<PagedList<AnnouncementModel>> {
            mAdapter?.submitList(it)
        })
    }

    private fun initAdapter() {
        mAdapter = AnnouncementAdapter()
        mBinding.announcementRecyclerView.layoutManager = LinearLayoutManager(mActivity)
        mBinding.announcementRecyclerView.adapter = mAdapter
    }

    override fun requestState(requestState: RequestState) {
        if (!requestState.isRootLoading)
            mAdapter?.setNetworkState(requestState)
    }
}