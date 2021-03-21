package com.xh.android

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.h.android.HAndroid
import com.h.android.adapter.AdapterViewListener
import com.h.android.dialog.BaseAlertDialog
import com.h.android.http.RxJavaHelper
import com.h.android.utils.HToast
import com.xh.android.databinding.ActivityMainBinding
import com.xh.android.databinding.AdapterLayoutBinding
import io.reactivex.Observable

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null

    private val adapter by lazy {
        MAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.showLoading.setOnClickListener {
            showLoading()
        }

        adapter.setAdapterViewListener(object : AdapterViewListener<AdapterLayoutBinding, String> {
            override fun viewListener(holder: AdapterLayoutBinding, view: View, t: String, pos: Int) {
                BaseAlertDialog(this@MainActivity)
                    .setDialogTitle("温馨提示")
                    .setDialogMessage("点击item" + t)
                    .addOkButton("OK", object : BaseAlertDialog.ActionBarListener {
                        override fun viewOnclickListener(textView: TextView?) {
                            if (holder.textTv == view) {
                                HToast.showToastNormal(t)
                            } else {
                                HToast.showToastNormal("--->" + t)
                            }
                        }
                    }).show()
            }
        })
        binding!!.recyclerView.adapter = adapter

        var dataList = mutableListOf<String>()
        for (i in 0..50) {
            dataList.add(i.toString())
        }
        adapter.bindData(true, dataList)

    }

    @SuppressLint("CheckResult")
    fun showLoading() {
        Observable.just(1)
            .map { it ->
                try {
                    Thread.sleep(4000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                it
            }
            .compose(RxJavaHelper.setDefaultConfig(this))
            .compose(HAndroid.bindToProgressHud(this))
            .subscribe()
    }
}
