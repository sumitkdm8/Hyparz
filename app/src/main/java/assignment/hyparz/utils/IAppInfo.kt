package assignment.hyparz.utils

import android.os.Build

interface IAppInfo {
    companion object {
        @JvmStatic
        fun isAndroidVersionAbove(versionCode: Int): Boolean {
            return Build.VERSION.SDK_INT >= versionCode
        }
    }
}
