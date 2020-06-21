package com.example.harusikdan.koin.modules

import com.example.harusikdan.api.usecase.GetFoodInfoUsecase
import org.koin.dsl.module

val usecaseModule = module {
    factory { GetFoodInfoUsecase(get()) }
}