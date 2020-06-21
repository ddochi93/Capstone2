package com.example.harusikdan.api.service

import com.example.harusikdan.data.request.FoodInfoRequest
import com.example.harusikdan.data.request.MenuInfoRequest
import com.example.harusikdan.data.response.FoodInfoResponse
import com.example.harusikdan.data.response.MenuInfoResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface MenuInfoService {
    @POST("findMenu")
    fun getMenuPosInfo(@Body menuList: MenuInfoRequest): Single<MenuInfoResponse>
}