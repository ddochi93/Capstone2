package com.example.harusikdan.koin.modules

import com.example.harusikdan.api.usecase.GetFoodInfoUsecase
import com.example.harusikdan.api.usecase.GetMenuPosInfoUsecase
import org.koin.dsl.module

val usecaseModule = module {
    factory { GetFoodInfoUsecase(get()) }
    factory { GetMenuPosInfoUsecase(get()) }
}