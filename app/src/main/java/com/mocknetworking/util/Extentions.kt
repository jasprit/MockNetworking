package com.mocknetworking.util


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.view.Window
import android.widget.Toast

import com.mocknetworking.R


fun createDialog(context: Context?): Dialog {
    val dialog = Dialog(context!!)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.progress_bar)
    dialog.setCancelable(false)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    return dialog
}

fun displayToast(mContext: Context?, msg: String) {
    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
}

fun Context.isNetworkConnected(): Boolean {
    val conMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = conMgr.activeNetworkInfo

    return if (info != null && info.isConnected) {
        true
    } else {
        displayToast(this, getString(R.string.error_internet_connection))
        false
    }
}


fun String.isStringHasNumber(): Boolean {
    return this.matches(Regex(".*\\d+.*"))
}
