package com.example.harusikdan.feature.takemenu

import android.graphics.Bitmap

interface MenuCaptureContract {
    interface View {
        fun setMenuPosOnImage(bitmap: Bitmap)
    }

    interface Presenter {
        val view: View
        fun getMenuPosInfo(menuList: ArrayList<String>, bitmap: Bitmap, rectList: ArrayList<RectPos>)
    }
}