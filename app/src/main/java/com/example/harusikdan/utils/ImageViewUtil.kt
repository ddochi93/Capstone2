package com.example.harusikdan.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import timber.log.Timber


fun ImageView.loadUriCenterCrop(uri : Uri) {
    val options = RequestOptions()
    GlideApp.with(this).load(uri).apply(options.centerCrop()).into(this)
    Timber.d("glide loaded %s", uri.toString())
}

fun ImageView.loadUrlCenterCrop(url : String) {
    val options = RequestOptions()
    Timber.d("loaded %s", url)
    GlideApp.with(this).load(url).apply(options.centerCrop()).into(this)
}

fun ImageView.loadUri(uri : Uri) {
    GlideApp.with(this).load(uri).into(this)
}

fun ImageView.loadUrl(url : String?) {
    url?.let {
        GlideApp.with(this).load(it).into(this)
    }
}

fun ImageView.loadDrawable(drawable: Drawable?) {
    drawable?.let {
        GlideApp.with(this).load(it).into(this)
    }
}

fun ImageView.loadGif(resource: Int) {
    GlideApp.with(this).asGif().load(resource).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(this)
}


//object ImageUtil {
//    private const val LOGTAG = "ImageUtil"
//
//    /*
//       * Picture를 Bitmap 파일로 저장
//       * viw : View ...
//       * fn : 파일 이름
//       * return : 성공 유무
//       */
//    fun PictureSaveToBitmapFile(viw: View?, fn: String?): Boolean {
//        var bRes = false
//        if (viw == null && fn == null && fn!!.length < 1) return bRes
//        viw.setDrawingCacheEnabled(true)
//        val bmp: Bitmap = viw.getDrawingCache() ?: return bRes
//        val file = File(fn)
//        var fOut: FileOutputStream? = null
//        try {
//            fOut = FileOutputStream(file)
//        } catch (e: FileNotFoundException) {
//            Log.e(LOGTAG, e.getMessage())
//            return bRes
//        }
//        bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut)
//        bRes = try {
//            fOut.flush()
//            fOut.close()
//            true
//        } catch (e: IOException) {
//            Log.e(LOGTAG, e.getMessage())
//            return bRes
//        }
//        return bRes
//    }
//
//    /*
//       * Bitmap 파일을 읽어오기
//       * fn : 파일 이름
//       * return : bitmap 이미지, 실패했을 경우 null
//       */
//    fun BitmapLoadFromFile(fn: String?): Bitmap? {
//        var bmp: Bitmap? = null
//        try {
//            bmp = BitmapFactory.decodeFile(fn)
//        } catch (e: Exception) {
//            Log.e(LOGTAG, e.message)
//        }
//        return bmp
//    }
//}
