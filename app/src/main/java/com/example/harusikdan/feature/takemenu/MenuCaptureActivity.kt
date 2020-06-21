package com.example.harusikdan.feature.takemenu

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.exifinterface.media.ExifInterface
import com.eroom.domain.utils.toastShort
import com.example.harusikdan.R
import com.example.harusikdan.data.entity.MenuPosInfo
import com.example.harusikdan.databinding.ActivityMenuCaptureBinding
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import org.json.JSONArray
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import org.koin.android.ext.android.get


class MenuCaptureActivity : AppCompatActivity(), MenuCaptureContract.View {
    // TODO: Rename and change types of parameters
    private lateinit var menuCaptureBinding: ActivityMenuCaptureBinding //체크해줘야함
    private val REQUEST_IMAGE_CAPTURE = 672
    private val GET_GALLERY_IMAGE = 200
    private var photoFile: File? = null
    private lateinit var imageFilePath: String
    private var photoUri: Uri? = null

    private lateinit var presenter: MenuCapturePresenter

    companion object {
        @JvmStatic
        fun newInstance() = MenuCaptureActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        settingPermission()
    }

    private fun initPresenter() {
        presenter = MenuCapturePresenter(this , get())
    }

    private fun setUpDataBinding() {
        menuCaptureBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_menu_capture)
        menuCaptureBinding.activity = this
    }


    private fun settingPermission() {
        var permis = object : PermissionListener {
            //            어떠한 형식을 상속받는 익명 클래스의 객체를 생성하기 위해 다음과 같이 작성
            override fun onPermissionGranted() {
                applicationContext.toastShort("권한 허가")
            }

            override fun onPermissionDenied(deniedPermissions: java.util.ArrayList<String>?) {
                applicationContext.toastShort("권한 거부")
                ActivityCompat.finishAffinity(this@MenuCaptureActivity) // 권한 거부시 앱 종료
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permis)
            .setRationaleMessage("카메라 사진 권한 필요")
            .setDeniedMessage("카메라 권한 요청 거부")
            .setPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .check()
    }


    fun startCapture() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.harusikdan.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, 101)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            imageFilePath = absolutePath
        }
    }

    fun galleryPreviewClicked() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            "image/*"
        )
        startActivityForResult(intent, GET_GALLERY_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101 && resultCode == Activity.RESULT_OK){
            val options : BitmapFactory.Options = BitmapFactory.Options()
            options.inSampleSize = 8;
            var bitmap : Bitmap = BitmapFactory.decodeFile(imageFilePath,options)


            var exif: ExifInterface? = null
            try {
                exif = ExifInterface(imageFilePath)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val exifOrientation: Int
            val exifDegree: Int

            if (exif != null) {
                exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
                exifDegree = exifOrientationToDegrees(exifOrientation)
            } else {
                exifDegree = 0
            }
            val image = FirebaseVisionImage.fromBitmap(rotate(bitmap, exifDegree.toFloat()))

            val detector = FirebaseVision.getInstance().cloudTextRecognizer

            Log.d("whatis?","before")
            var stri : String = "" //화면에 보여줄 String값

            //var p = Array<IntArray>(4,{ IntArray(2) })
            var rectList: ArrayList<RectPos> = ArrayList()
            var textList: ArrayList<String> = ArrayList()

            val result = detector.processImage(image).addOnSuccessListener { firebaseVisionText -> //사진에서 글자인식하고 return한 값 분석
                val resultText = firebaseVisionText.text
                for (block in firebaseVisionText.textBlocks) {
                    val blockText = block.text
                    val blockConfidence = block.confidence
                    val blockLanguages = block.recognizedLanguages
                    val blockCornerPoints = block.cornerPoints

                    val blockFrame = block.boundingBox
                    for (line in block.lines) { //라인 단위로 끊어서 확인
                        val lineText = line.text
                        val lineConfidence = line.confidence
                        val lineLanguages = line.recognizedLanguages
                        val lineCornerPoints = line.cornerPoints
                        val lineFrame = line.boundingBox
                        stri = stri + lineText +'\n' //화면에 출력되는 문자는 line의 text값들이다.
                        textList.add(lineText)

                        rectList.add(RectPos(
                            Point(lineFrame!!.left,lineFrame.top),
                            Point(lineFrame.right,lineFrame.bottom), 0))
                    }
                }
                val jsArray = JSONArray(textList)
                val i  = 0

                //------------------------
                var fileName: String? = "myImage" //no .png or .jpg needed

                bitmap = image.bitmap

                var a = textList

                presenter.getMenuPosInfo(textList, bitmap, rectList)
//                var sendList: ArrayList<RectPos> = ArrayList()
//                for(i in menuPos){
//                    sendList.add(RectPos(Point(rectList[i[0]].leftTop),Point(rectList[i[0]].rightBottom),i[1]))
//                }
//
//                sendList.map {
//
//                    if(it.leftTop!!.x>0){
//                        it.leftTop!!.x--
//                    }
//                    if(it.leftTop!!.y>0){
//                        it.leftTop!!.y--
//                    }
//                    if(it.rightBottom!!.x>0){
//                        it.rightBottom!!.x--
//                    }
//                    if(it.rightBottom!!.y>0){
//                        it.rightBottom!!.y--
//                    }
//
//                    bitmap = drawRect(it.leftTop!!.x, it.leftTop!!.y, it.rightBottom!!.x, it.rightBottom!!.y, bitmap, it.color!!)
//                }
//                menuCaptureBinding.imgPicture.setImageBitmap(bitmap)
            }


        }
    }

    override fun setMenuPosOnImage(bitmap: Bitmap) {
        menuCaptureBinding.imgPicture.setImageBitmap(bitmap)
    }


    private fun exifOrientationToDegrees(exifOrientation: Int): Int {
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270
        }
        return 0
    }

    private fun rotate(bitmap: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree)
        return Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    }
    private fun drawRect(x1 : Int, y1 : Int, x2 : Int, y2 : Int, bitmap : Bitmap, c : Int):Bitmap {
        val temp : Int
        if(c ==0){
            temp = 0x00ff00
        }else if(c ==1){
            temp = 0xffff00
        }else{
            temp = 0xff0000
        }
        for (i in x1 until x2) {
            bitmap.setPixel(i,y1,temp)
            bitmap.setPixel(i,y2,temp)
        }

        for (i in y1 until y2) {
            bitmap.setPixel(x1,i,temp)
            bitmap.setPixel(x2,i,temp)
        }

        return bitmap
    }
}