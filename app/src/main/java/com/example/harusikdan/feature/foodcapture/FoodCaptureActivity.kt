package com.example.harusikdan.feature.foodcapture

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.eroom.domain.utils.toastShort
import com.example.harusikdan.R
import com.example.harusikdan.databinding.ActivityFoodCaptureBinding
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_food_capture.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FoodCaptureActivity : AppCompatActivity() {
    private lateinit var activityFoodCaptureBinding: ActivityFoodCaptureBinding
    private val REQUEST_IMAGE_CAPTURE = 672
    private val GET_GALLERY_IMAGE = 200
    private var photoFile: File? = null
    private var imageFilePath: String? = null
    private var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        settingPermission()
    }

    private fun setUpDataBinding() {
        activityFoodCaptureBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_food_capture)
        activityFoodCaptureBinding.activity = this
    }


    private fun settingPermission() {
        var permis = object : PermissionListener {
            //            어떠한 형식을 상속받는 익명 클래스의 객체를 생성하기 위해 다음과 같이 작성
            override fun onPermissionGranted() {
                applicationContext.toastShort("권한 허가")
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                applicationContext.toastShort("권한 거부")
                ActivityCompat.finishAffinity(this@FoodCaptureActivity) // 권한 거부시 앱 종료
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

//    fun captureButtonClicked() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        if (intent.resolveActivity(this.packageManager) != null) {
//            photoFile = null
//            try {
//                photoFile = createImageFile()
//            } catch (e: IOException) {
//            }
//            if (photoFile != null) {
//                photoUri = FileProvider.getUriForFile(this, this.packageName, photoFile!!)
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
//                startActivityForResult(
//                    intent, REQUEST_IMAGE_CAPTURE
//                )
//            }
//        }
//    }

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
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }


    //    @SuppressLint("SimpleDateFormat")
//    @Throws(IOException::class)
//    private fun createImageFile(): File? {
//        val timeStamp =
//            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        val imageFileName = "TEST_" + timeStamp + "_"
//        val storageDir: File? = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        val image = File.createTempFile(
//            imageFileName,
//            ".jpg",
//            storageDir
//        )
//        imageFilePath = image.absolutePath
//        return image
//    }
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

    /**
     * Bitmap이미지의 가로, 세로 사이즈를 리사이징 한다.
     *
     * @param source        원본 Bitmap 객체
     * @param maxResolution 제한 해상도
     * @return 리사이즈된 이미지 Bitmap 객체
     */
    fun resizeBitmapImage(source: Bitmap, maxResolution: Int): Bitmap? {
        val width = source.width
        val height = source.height
        var newWidth = width
        var newHeight = height
        var rate = 0.0f
        if (width > height) {
            if (maxResolution < width) {
                rate = maxResolution / width.toFloat()
                newHeight = (height * rate).toInt()
                newWidth = maxResolution
            }
        } else {
            if (maxResolution < height) {
                rate = maxResolution / height.toFloat()
                newWidth = (width * rate).toInt()
                newHeight = maxResolution
            }
        }
        return Bitmap.createScaledBitmap(source, newWidth, newHeight, true)
    }

    fun runTensorflow() {
        val bitmap = (activityFoodCaptureBinding.imgPicture.drawable as BitmapDrawable).bitmap
        val resizedBitmap = resizeBitmapImage(bitmap, 299)
        //여기에 추가해라 현석
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val file = File(imageFilePath)
            if (Build.VERSION.SDK_INT < 28) {
                val bitmap = MediaStore.Images.Media
                    .getBitmap(contentResolver, Uri.fromFile(file))
                img_picture.setImageBitmap(bitmap)
            } else {
                val decode = ImageDecoder.createSource(
                    this.contentResolver,
                    Uri.fromFile(file)
                )
                val bitmap = ImageDecoder.decodeBitmap(decode)
                img_picture.setImageBitmap(bitmap)
            }
        }

        if (requestCode == GET_GALLERY_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val selectedImageUri = data.data
            activityFoodCaptureBinding.galleryPreview.setImageURI(selectedImageUri)
            activityFoodCaptureBinding.imgPicture.setImageURI(
                selectedImageUri
            )
            activityFoodCaptureBinding.tensorButton.visibility = View.VISIBLE
        }
    }
}