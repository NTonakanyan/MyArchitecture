package com.example.myarchitecture.view.baseView

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    protected val mActivity: BaseActivity by lazy { activity as BaseActivity }
}