package com.example.myarchitecture.view.baseView

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

open class BaseFragment : Fragment() {
    protected val mActivity: BaseActivity by lazy { requireActivity() as BaseActivity }

    fun <T : ViewModel?> createViewModel(viewModel: Class<T>): T {
        return ViewModelProvider(this).get(viewModel)
    }
}