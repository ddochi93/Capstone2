package com.example.harusikdan.api.usecase

import com.example.harusikdan.api.service.FoodInfoService
import com.example.harusikdan.api.service.MenuInfoService
import com.example.harusikdan.data.request.FoodInfoRequest
import com.example.harusikdan.data.request.MenuInfoRequest
import com.example.harusikdan.data.response.FoodInfoResponse
import com.example.harusikdan.data.response.MenuInfoResponse
import com.example.harusikdan.koin.repository.AccessRetrofitRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class GetMenuPosInfoUsecase(accessRetrofitRepository: AccessRetrofitRepository) {
    private val menuInfoService = accessRetrofitRepository
        .getAccessRetrofit()
        .create(MenuInfoService::class.java)

    fun getMenuPosInfo(menuInfoRequest: MenuInfoRequest) : Single<MenuInfoResponse> = menuInfoService
        .getMenuPosInfo(menuInfoRequest)
        .subscribeOn(Schedulers.io())
        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())

}