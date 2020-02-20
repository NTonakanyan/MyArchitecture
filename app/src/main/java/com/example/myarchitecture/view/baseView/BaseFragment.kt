package com.example.myarchitecture.view.baseView

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myarchitecture.shared.customViews.StateLayout
import com.example.myarchitecture.shared.data.networking.RequestState

open class BaseFragment : Fragment() {
    protected val mActivity: BaseActivity by lazy { requireActivity() as BaseActivity }

    fun <T : BaseViewModel?> createViewModel(viewModel: Class<T>): T {
        return ViewModelProvider(this).get(viewModel)
    }

    fun <T : BaseViewModel> requestSubscriber(viewModel: T, stateLayout: StateLayout) {
        viewModel.mRequestLiveData.observe(viewLifecycleOwner, Observer<RequestState> {
            if (it.isRootLoading)
                when (it.status) {
                    RequestState.Status.EMPTY -> stateLayout.showEmpty()
                    RequestState.Status.NETWORK_ERROR -> stateLayout.showNetworkError()
                    RequestState.Status.SERVER_ERROR -> stateLayout.showServerError()
                    RequestState.Status.API_ERROR -> stateLayout.showServerError()
                    RequestState.Status.LOADING -> stateLayout.showLoading()
                    RequestState.Status.SUCCESS -> stateLayout.showContent()
                }
            requestState(it)
        })
    }

    open fun requestState(requestState: RequestState) {}
}