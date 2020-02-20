package com.example.myarchitecture.view.mainActivity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myarchitecture.R
import com.example.myarchitecture.databinding.MainBinding
import com.example.myarchitecture.view.baseView.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var mBinding: MainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initView()
    }

    private fun initView() {
        val navController = findNavController(R.id.main_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_announcement))
        setupActionBarWithNavController(navController, appBarConfiguration)
        mBinding.mainNavigation.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}