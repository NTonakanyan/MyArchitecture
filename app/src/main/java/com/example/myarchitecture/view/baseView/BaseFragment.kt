package com.example.myarchitecture.view.baseView

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.myarchitecture.shared.customViews.StateLayout
import com.example.myarchitecture.shared.data.networking.RequestState

open class BaseFragment : Fragment() {

    protected val mActivity: BaseActivity by lazy { requireActivity() as BaseActivity }
    private var mViewModel: BaseViewModel? = null

    fun <T : BaseViewModel> requestSubscriber(viewModel: T, stateLayout: StateLayout) {
        mViewModel = viewModel
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            requestState(it)
            when (it.status) {
                RequestState.Status.EMPTY -> stateLayout.showEmpty(it)
                RequestState.Status.NETWORK_ERROR -> stateLayout.showNetworkError(it)
                RequestState.Status.SERVER_ERROR -> stateLayout.showServerError(it)
                RequestState.Status.API_ERROR -> stateLayout.showServerError(it)
                RequestState.Status.LOADING -> stateLayout.showLoading(it)
                RequestState.Status.SUCCESS -> stateLayout.showContent(it)
            }
        })
    }

    open fun requestState(requestState: RequestState) {}

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel?.closeRequest()
    }
}