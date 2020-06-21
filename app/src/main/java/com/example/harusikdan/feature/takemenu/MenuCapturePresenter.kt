package com.example.harusikdan.feature.takemenu

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Point
import android.util.Log
import com.example.harusikdan.api.usecase.GetFoodInfoUsecase
import com.example.harusikdan.api.usecase.GetMenuPosInfoUsecase
import com.example.harusikdan.data.entity.Food
import com.example.harusikdan.data.entity.MenuPosInfo
import com.example.harusikdan.data.request.FoodInfoRequest
import com.example.harusikdan.data.request.MenuInfoRequest
import com.example.harusikdan.feature.foodcapture.FoodSelectContract

class MenuCapturePresenter(
    override val view: MenuCaptureContract.View,
    private val getMenuPosInfoUsecase: GetMenuPosInfoUsecase
) : MenuCaptureContract.Presenter {

    @SuppressLint("CheckResult")
    override fun getMenuPosInfo(menuList: ArrayList<String>, bitmap: Bitmap, rectList: ArrayList<RectPos>) {
        var bitmap2 = bitmap
        var req = ArrayList<String>()
        for(elem in menuList) {
            req.add(elem)
        }
        val request = MenuInfoRequest(req)
        getMenuPosInfoUsecase.getMenuPosInfo(request)
            .subscribe({
                for(i in it.menu_result) {
                    val list = ArrayList<Int>()
                    for(j in i ) {
                        list.add(j)
                    }
                    MenuPosInfo.menuPosInfo.add(list)
                    var a = MenuPosInfo.menuPosInfo


                }


                var sendList: ArrayList<RectPos> = ArrayList()
                for(i in MenuPosInfo.menuPosInfo){
                    sendList.add(RectPos(
                        Point(rectList[i[0]].leftTop),
                        Point(rectList[i[0]].rightBottom),i[1]))
                }

                sendList.map {

                    if(it.leftTop!!.x>0){
                        it.leftTop!!.x--
                    }
                    if(it.leftTop!!.y>0){
                        it.leftTop!!.y--
                    }
                    if(it.rightBottom!!.x>0){
                        it.rightBottom!!.x--
                    }
                    if(it.rightBottom!!.y>0){
                        it.rightBottom!!.y--
                    }

                    bitmap2 = drawRect(it.leftTop!!.x, it.leftTop!!.y, it.rightBottom!!.x, it.rightBottom!!.y, bitmap, it.color!!)
                }
                view.setMenuPosOnImage(bitmap2)
            }, {
                Log.e("MenuCapturePresenter", "server error")
            })
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
