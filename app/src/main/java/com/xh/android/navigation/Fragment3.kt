package com.xh.android.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.h.android.fragment.HFragment
import com.h.android.utils.FragmentNavigationUtil
import com.xh.android.databinding.Fragment3LayoutBinding

/**
 *2021/8/23
 *@author zhangxiaohui
 *@describe
 */
class Fragment3 : HFragment() {
    var binding: Fragment3LayoutBinding? = null

    override fun onCreateViewOnce(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = Fragment3LayoutBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreateOnce(view: View, savedInstanceState: Bundle?) {
        super.onViewCreateOnce(view, savedInstanceState)


        binding!!.fragment1Button.setOnClickListener {
            FragmentNavigationUtil.get().removeCurrentFragment(it)
        }
    }
}