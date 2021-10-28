package com.xh.android

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.h.android.HAndroid
import com.h.android.activity.HActivity
import com.h.android.adapter.AdapterViewListener
import com.h.android.dialog.BaseAlertDialog
import com.h.android.http.RxJavaHelper
import com.h.android.utils.HLog
import com.h.android.utils.HToast
import com.xh.android.databinding.ActivityMainBinding
import com.xh.android.databinding.AdapterLayoutBinding
import io.reactivex.Observable
import java.security.Permissions

class MainActivity : HActivity() {
    var binding: ActivityMainBinding? = null

    private val adapter by lazy {
        MAdapter()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.showLoading.setOnClickListener {
            requestPermissionForResultNowTest()
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

    /**
     * 测试startActivityForResultNow
     */
    private fun startActivityForResultNowTest() {
        val intent = Intent(this, MainActivity1::class.java)
        startActivityForResultNow(ActivityResultCallback<ActivityResult> {
            /**
             * Called when result is available
             */
            HToast.showToastSuccess(it.data?.getStringExtra("key")?.toString() ?: "--")
        }, intent)
    }

    /**
     * 测试requestPermissionForResultNow
     */
    private fun requestPermissionForResultNowTest() {
        requestPermissionForResultNow(ActivityResultCallback {
            it.forEach { (t, u) ->
                HToast.showToastSuccess("$t-$u")
            }
        }, android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

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
            .`as`(HAndroid.autoDisposable(this))
            .subscribe()
    }
}
