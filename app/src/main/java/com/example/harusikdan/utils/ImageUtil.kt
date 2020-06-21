package com.example.harusikdan.utils

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import android.view.View
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

object ImageUtil {
    private const val LOGTAG = "ImageUtil"

    /*
       * Picture를 Bitmap 파일로 저장
       * viw : View ...
       * fn : 파일 이름
       * return : 성공 유무
       */
    fun PictureSaveToBitmapFile(viw: View, fn: String): Boolean {
        var bRes = false
        if (viw == null && fn == null && fn.length < 1) return bRes
        viw.isDrawingCacheEnabled = true
        val bmp: Bitmap = viw.drawingCache ?: return bRes


        val file = File(Environment.getExternalStorageDirectory().absolutePath + "/${fn}")
        var fOut: FileOutputStream? = null
        try {
            fOut = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            Log.e(LOGTAG, e.message)
            return bRes
        }
        bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut)
        bRes = try {
            fOut.flush()
            fOut.close()
            true
        } catch (e: IOException) {
            Log.e(LOGTAG, e.message)
            return bRes
        }
        return bRes
    }

    /*
       * Bitmap 파일을 읽어오기
       * fn : 파일 이름
       * return : bitmap 이미지, 실패했을 경우 null
       */
    fun BitmapLoadFromFile(fn: String?): Bitmap? {
        var bmp: Bitmap? = null
        try {
            bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().absolutePath + "/${fn}")
        } catch (e: Exception) {
            Log.e(LOGTAG, e.message)
        }
        return bmp
    }
}
