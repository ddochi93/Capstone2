package com.example.harusikdan.feature.foodcapture

import android.annotation.SuppressLint
import android.util.Log
import com.example.harusikdan.api.usecase.GetFoodInfoUsecase
import com.example.harusikdan.data.entity.Food
import com.example.harusikdan.data.request.FoodInfoRequest

class FoodSelectPresenter(
    override val view: FoodSelectContract.View,
    private val getFoodInfoUsecase: GetFoodInfoUsecase
) : FoodSelectContract.Presenter {

    @SuppressLint("CheckResult")
    override fun getFoodInfoByFoodName(foodName: String) {
        getFoodInfoUsecase.getFoodInfo(FoodInfoRequest(food_name = foodName))
            .subscribe({
                Food.calorie = it.foodinfo.food_kcal
                Food.carbohydrate = it.foodinfo.food_carbo
                Food.protein = it.foodinfo.food_protein
                Food.fat = it.foodinfo.food_fat
                Food.salt = it.foodinfo.food_salt
                Food.sugar = it.foodinfo.food_sugar
                Log.e("FoodSelectPresenter", "${Food.calorie}, ${Food.carbohydrate} , ${Food.protein} , ${Food.fat} ,${Food.salt} , ${Food.sugar}")
            }, {
                Log.e("FoodSelectPresenter", "server error")
            })
    }
}