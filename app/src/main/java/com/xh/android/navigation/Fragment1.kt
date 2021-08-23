package com.xh.android.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.h.android.fragment.HFragment
import com.h.android.utils.FragmentNavigationUtil
import com.xh.android.R
import com.xh.android.databinding.Fragment1LayoutBinding

/**
 *2021/8/23
 *@author zhangxiaohui
 *@describe
 */
class Fragment1 : HFragment() {
    var binding: Fragment1LayoutBinding? = null

    override fun onCreateViewOnce(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateViewOnce(inflater, container, savedInstanceState)
        binding = Fragment1LayoutBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreateOnce(view: View, savedInstanceState: Bundle?) {
        super.onViewCreateOnce(view, savedInstanceState)

        binding!!.fragment1Button.setOnClickListener {
            FragmentNavigationUtil.get().startFragment(it,R.id.action_fragment1_to_fragment22)
        }
    }
}