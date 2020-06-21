package com.example.harusikdan.feature.foodcapture


interface FoodSelectContract {
    interface View {
        fun finishView()
    }

    interface Presenter {
        val view: FoodSelectContract.View
        fun getFoodInfoByFoodName(foodName: String)
    }
}