package com.example.myarchitecture.view.mainActivity.fragments.announcementDetailsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.myarchitecture.databinding.AnnouncementDetailsBinding
import com.example.myarchitecture.shared.di.scopes.PersonScope
import com.example.myarchitecture.shared.utils.AppConstants
import com.example.myarchitecture.view.baseView.BaseFragment

class AnnouncementDetailsFragment : BaseFragment() {

    private lateinit var mBinding: AnnouncementDetailsBinding
    private val mViewModel by viewModels<AnnouncementDetailsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = AnnouncementDetailsBinding.inflate(inflater, container, false)

        initSubscribers()

        mViewModel.getAnnouncementDetails(arguments?.getInt(AppConstants.ID))

        return mBinding.root
    }

    private fun initSubscribers() {
        requestSubscriber(mViewModel, mBinding.announcementDetailsStateLayout)

        mViewModel.getAnnouncementDetailsLiveData().observe(viewLifecycleOwner, Observer {
            mBinding.model = it
        })
    }
}