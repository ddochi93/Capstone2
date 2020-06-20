package com.example.harusikdan.feature.foodcapture

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
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
import org.tensorflow.lite.Interpreter
import java.io.*
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
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
        return Bitmap.createScaledBitmap(source, 299, 299, true)
    }

    fun runTensorflow() {
        val bitmap = (activityFoodCaptureBinding.imgPicture.drawable as BitmapDrawable).bitmap
        val resizedBitmap = resizeBitmapImage(bitmap, 299)

        val output = Array(1) { FloatArray(104) }
        val label = arrayOfNulls<String>(104)
        val am = assets
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(InputStreamReader(am.open("output_kr_labels.txt")))
            var tmp: String?
            var line_i = 0
            while (reader.readLine().also { tmp = it } != null) {
                label[line_i] = tmp
                line_i++
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        Log.d("FoodCaptureActivity", "done read label")

        try {
            val batchNum = 0
            val input = Array(1) { Array(299) { Array(299) { FloatArray(3) } } }
            for (x in 0..298) {
                for (y in 0..298) {
                    val pixel = resizedBitmap!!.getPixel(x, y)
                    input[batchNum][x][y][0] = Color.red(pixel) / 1.0f
                    input[batchNum][x][y][1] = Color.green(pixel) / 1.0f
                    input[batchNum][x][y][2] = Color.blue(pixel) / 1.0f
                }
            }
            val lite = getTfliteInterpreter("lite_graph_2.tflite")
            lite!!.run(input, output)
            var maxVal = 0.0f
            var index_1 = 0
            var index_2 = 0
            var index_3 = 0
            var index_4 = 0
            for (i in 0..103) {
                if (output[0][i] > maxVal) {
                    maxVal = output[0][i]
                    index_4 = index_3
                    index_3 = index_2
                    index_2 = index_1
                    index_1 = i
                }
            }
            val first = label[index_1]
            val second = label[index_2]
            val third = label[index_3]
            val forth = label[index_4]

            val result = arrayOf(first, second, third, forth)
            Log.d("FoodCaptureActivity", first)
            Log.d("FoodCaptureActivity", second)
            Log.d("FoodCaptureActivity", third)
            Log.d("FoodCaptureActivity", forth)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getTfliteInterpreter(modelPath: String): Interpreter? {
        try {
            return Interpreter(loadModelFile(this, modelPath));
        } catch (e: Exception) {
            e.printStackTrace();
        }
        return null;
    }

    @Throws(IOException::class)
    fun loadModelFile(activity: Activity, modelPath: String): MappedByteBuffer {
        val fileDescriptor: AssetFileDescriptor = activity.getAssets().openFd(modelPath);
        val inputStream: FileInputStream = FileInputStream(fileDescriptor.getFileDescriptor());
        val fileChannel: FileChannel = inputStream.getChannel();
        val startOffset: Long = fileDescriptor.getStartOffset();
        val declaredLength: Long = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
//    fun getTfliteInterpreter : Interpreter ( modelPath : String) {
//        try {
//            return new Interpreter(loadModelFile(FoodCaptureActivity.this, modelPath));
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    fun loadModelFile: MappedByteBuffer (Activity activity, String modelPath) throws IOException {
//        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(modelPath);
//        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
//        FileChannel fileChannel = inputStream.getChannel();
//        startOffset : Long = fileDescriptor.getStartOffset();
//        declaredLength : Long = fileDescriptor.getDeclaredLength();
//        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
//    }

    private fun exifOrientationToDegrees(exifOrientation: Int): Int {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270
        }
        return 0
    }

    private fun rotate(bitmap: Bitmap, degree: Float): Bitmap? {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val bitmap = BitmapFactory.decodeFile(imageFilePath)
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
            activityFoodCaptureBinding.imgPicture.setImageBitmap(
                rotate(bitmap, exifDegree.toFloat())
            )
            activityFoodCaptureBinding.tensorButton.visibility = View.VISIBLE
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