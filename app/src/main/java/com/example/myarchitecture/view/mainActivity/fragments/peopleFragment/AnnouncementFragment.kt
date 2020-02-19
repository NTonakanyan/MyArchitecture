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
    private val mViewModel: AnnouncementViewModel by lazy { mActivity.createViewModel(AnnouncementViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = AnnouncementBinding.inflate(inflater, container, false)

        mViewModel.getAnnouncements()

        initSubscribers()

        initAdapter()

        return mBinding.root
    }

    private fun initSubscribers() {
        mViewModel.getAnnouncementsLiveData()?.observe(this, Observer<PagedList<AnnouncementModel>> {
            mAdapter?.submitList(it)
        })

        mViewModel.mErrorLiveData.observe(this, Observer<RequestState> {
            if (!it.isRootLoading)
                mAdapter?.setNetworkState(it)
            else {
                when (it.status) {
                    RequestState.Status.EMPTY ->
                        mBinding.announcementStateLayout.showEmpty()
                    RequestState.Status.NETWORK_ERROR ->
                        mBinding.announcementStateLayout.showNetworkError()
                    RequestState.Status.SERVER_ERROR ->
                        mBinding.announcementStateLayout.showServerError()
                    RequestState.Status.API_ERROR ->
                        mBinding.announcementStateLayout.showServerError()
                    RequestState.Status.LOADING ->
                        mBinding.announcementStateLayout.showLoading()
                    RequestState.Status.SUCCESS ->
                        mBinding.announcementStateLayout.showContent()
                }
            }
        })
    }

    private fun initAdapter() {
        mAdapter = AnnouncementAdapter()
        mBinding.announcementRecyclerView.layoutManager = LinearLayoutManager(mActivity)
        mBinding.announcementRecyclerView.adapter = mAdapter
    }
}