package com.example.myarchitecture.view.mainActivity.fragments.announcementFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myarchitecture.R
import com.example.myarchitecture.databinding.FragmentAnnouncementBinding
import com.example.myarchitecture.shared.data.networking.RequestState
import com.example.myarchitecture.shared.utils.AppConstants
import com.example.myarchitecture.view.adapters.AnnouncementAdapter
import com.example.myarchitecture.view.baseView.BaseFragment

class AnnouncementFragment : BaseFragment() {

    private lateinit var mBinding: FragmentAnnouncementBinding
    private val mViewModel by viewModels<AnnouncementViewModel>()
    private var mAdapter = AnnouncementAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.getAnnouncements()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentAnnouncementBinding.inflate(inflater, container, false)

        initAdapter()

        initSubscribers()

        initView()

        return mBinding.root
    }

    private fun initView() {
        mBinding.announcementSwipeRefresh.setOnRefreshListener {
            mViewModel.getAnnouncements(false)
        }
    }

    private fun initSubscribers() {
        requestSubscriber(mViewModel, mBinding.announcementStateLayout)

        mViewModel.getAnnouncementsLiveData().observe(viewLifecycleOwner, Observer {
            mAdapter.submitList(it)
        })
    }

    private fun initAdapter() {
        mAdapter.setOnItemClickListener(object : AnnouncementAdapter.OnItemClickListener {
            override fun onClick(id: Int) {
                val navController = Navigation.findNavController(mActivity, R.id.main_fragment)
                val args = Bundle()
                args.putInt(AppConstants.ID, id)
                navController.navigate(R.id.action_announcement_to_announcement_details, args)
            }
        })
        mBinding.announcementRecyclerView.layoutManager = LinearLayoutManager(mActivity)
        mBinding.announcementRecyclerView.adapter = mAdapter
    }

    override fun requestState(requestState: RequestState) {
        if (!requestState.isMainRequest)
            mAdapter.setNetworkState(requestState)
    }
}