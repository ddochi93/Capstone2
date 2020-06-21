package com.example.harusikdan.api.service

import com.example.harusikdan.data.request.FoodInfoRequest
import com.example.harusikdan.data.response.FoodInfo
import com.example.harusikdan.data.response.FoodInfoResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodInfoService {
    @POST("sendFoodname")
    fun getFoodInfo(@Body foodName: FoodInfoRequest): Single<FoodInfoResponse>
}