package com.example.harusikdan.feature.foodcapture

import android.annotation.SuppressLint
import android.util.Log
import com.example.harusikdan.api.usecase.GetFoodInfoUsecase
import com.example.harusikdan.data.entity.Food
import com.example.harusikdan.data.entity.FoodVO
import com.example.harusikdan.data.request.FoodInfoRequest
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class FoodSelectPresenter(
    override val view: FoodSelectContract.View,
    private val getFoodInfoUsecase: GetFoodInfoUsecase
) : FoodSelectContract.Presenter {
    private val realm = Realm.getDefaultInstance()

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
                insertFoodToDb()
                Log.e("FoodSelectPresenter", "${Food.calorie}, ${Food.carbohydrate} , ${Food.protein} , ${Food.fat} ,${Food.salt} , ${Food.sugar}")
                view.finishView()
            }, {
                Log.e("FoodSelectPresenter", "server error")
            })
    }

    private fun insertFoodToDb() {
        realm.beginTransaction()

        val food = realm.createObject<FoodVO>(nextId())
        food.foodName = Food.foodName.toString()
        food.date = Food.date.toString()
        food.mealTime = Food.mealTime.toString()
        food.calorie = Food.calorie
        food.carbohydrate = Food.carbohydrate
        food.protein = Food.protein
        food.fat = Food.fat
        food.sugar = Food.sugar
        food.salt = Food.salt

        realm.commitTransaction()
    }

    private fun nextId(): Int {
        val maxId = realm.where<FoodVO>().max("id")
        if(maxId != null) {
            return maxId.toInt() + 1
        }
        return 0
    }
}