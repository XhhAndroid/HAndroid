package com.xh.android

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.h.android.adapter.AdapterViewListener
import com.h.android.adapter.HViewHolder
import com.h.android.utils.HToast
import com.xh.android.databinding.ActivityMainBinding
import com.xh.android.databinding.AdapterLayoutBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null

    private val adapter by lazy {
        MAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        adapter.setAdapterViewListener(object : AdapterViewListener<AdapterLayoutBinding, String> {
            override fun viewListener(holder: AdapterLayoutBinding, view: View, t: String, pos: Int) {
                if (holder.textTv == view) {
                    HToast.showToastNormal(t)
                } else {
                    HToast.showToastNormal("--->" + t)
                }
            }


        })
        recyclerView.adapter = adapter

        var dataList = mutableListOf<String>()
        for (i in 0..50) {
            dataList.add(i.toString())
        }
        adapter.bindData(true, dataList)

    }
}
