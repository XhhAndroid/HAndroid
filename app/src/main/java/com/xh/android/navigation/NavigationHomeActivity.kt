package com.xh.android.navigation

import android.os.Bundle
import com.h.android.activity.HActivity
import com.h.android.utils.FragmentNavigationUtil
import com.xh.android.R
import com.xh.android.databinding.NavigationHomeActivityLayoutBinding

/**
 *2021/8/23
 *@author zhangxiaohui
 *@describe
 */
class NavigationHomeActivity : HActivity() {
    var binding: NavigationHomeActivityLayoutBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NavigationHomeActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.startButton.setOnClickListener {
            FragmentNavigationUtil.get()
                .replaceFragment(binding!!.fragmentContainer, R.navigation.home_navigation, R.id.fragment2)
//            navController.navigateUp()
        }
    }
}