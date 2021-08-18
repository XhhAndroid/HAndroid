### 跳转带返回值 startActivityForResultNow()

    val intent = Intent(this, MainActivity1::class.java)
        startActivityForResultNow(ActivityResultCallback<ActivityResult> {
            /**
             * Called when result is available
             */
            HToast.showToastSuccess(it.data?.getStringExtra("key")?.toString() ?: "--")
        }, intent)

### 权限请求 requestPermissionForResultNowTest()

    requestPermissionForResultNow(ActivityResultCallback {
            it.forEach { (t, u) ->
                HToast.showToastSuccess("$t-$u")
            }
        }, android.Manifest.permission.READ_EXTERNAL_STORAGE)
