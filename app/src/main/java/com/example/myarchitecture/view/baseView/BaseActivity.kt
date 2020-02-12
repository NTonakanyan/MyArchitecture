package com.example.myarchitecture.view.baseView

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    fun <T : ViewModel?> createViewModel(viewModel: Class<T>): T {
        return ViewModelProvider(this).get(viewModel)
    }
}