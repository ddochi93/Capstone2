package com.example.harusikdan.api.usecase

import com.example.harusikdan.api.service.FoodInfoService
import com.example.harusikdan.data.request.FoodInfoRequest
import com.example.harusikdan.data.response.FoodInfoResponse
import com.example.harusikdan.koin.repository.AccessRetrofitRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import rx.android.schedulers.AndroidSchedulers

class GetFoodInfoUsecase(accessRetrofitRepository: AccessRetrofitRepository) {
    private val foodInfoService = accessRetrofitRepository
        .getAccessRetrofit()
        .create(FoodInfoService::class.java)

    fun getFoodInfo(foodInfoRequest: FoodInfoRequest) : Single<FoodInfoResponse> = foodInfoService
        .getFoodInfo(foodInfoRequest)
        .subscribeOn(Schedulers.io())
        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())

}