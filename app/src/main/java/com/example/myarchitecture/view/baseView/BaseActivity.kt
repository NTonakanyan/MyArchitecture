package com.example.myarchitecture.view.baseView

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.armboldmind.exceptionlibrary.ExceptionHandler

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ExceptionHandler.setUCEHandler()
    }
}