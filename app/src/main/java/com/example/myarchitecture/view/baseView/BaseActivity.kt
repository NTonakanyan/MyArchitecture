package com.example.myarchitecture.view.baseView

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myarchitecture.shared.customViews.StateLayout
import com.example.myarchitecture.shared.data.networking.RequestState

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    fun <T : ViewModel?> createViewModel(viewModel: Class<T>): T {
        return ViewModelProvider(this).get(viewModel)
    }
}