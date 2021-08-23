package com.h.android.utils

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.h.android.R

/**
 *2021/8/23
 *@author zhangxiaohui
 *@describe 采用navigation方式管理fragment
 */
class FragmentNavigationUtil {

    companion object {
        private var instance: FragmentNavigationUtil? = null
            get() {
                if (field == null) {
                    field = FragmentNavigationUtil()
                }
                return field
            }

        fun get(): FragmentNavigationUtil {
            return instance!!
        }
    }

    /**
     * 替换fragment
     * @describe 此方法会把fragment创建一个新实例
     * @param fragmentContainer activity中装载fragment的容器 一般为androidx.fragment.app.FragmentContainerView
     * @param navigationResource res下定义的navigation文件
     * @param targetFragmentId 目标fragment的id
     */
    fun replaceFragment(fragmentContainer: View, navigationResource: Int, targetFragmentId: Int, bundle: Bundle?) {
        val navController = Navigation.findNavController(fragmentContainer)
        val navInflater = navController.navInflater
        val navGraph = navInflater.inflate(navigationResource)
        navGraph.startDestination = targetFragmentId
        navController.setGraph(navGraph, bundle)
    }

    /**
     *@see #replaceFragment(View, Int, Int, Bundle)
     */
    fun replaceFragment(fragmentContainer: View, navigationResource: Int, targetFragmentId: Int) {
        replaceFragment(fragmentContainer, navigationResource, targetFragmentId, null)
    }

    /**
     * 退出当前fragment
     * @param view 点击事件的view
     */
    fun removeCurrentFragment(view: View) {
        val navController = Navigation.findNavController(view)
        navController.navigateUp()
    }

    /**
     * 启动fragment
     * @param view 点击事件的view
     * @param navigationId navigation文件中定义的action id
     */
    fun startFragment(view: View, navigationId: Int) {
        Navigation.findNavController(view).navigate(navigationId)
    }
}