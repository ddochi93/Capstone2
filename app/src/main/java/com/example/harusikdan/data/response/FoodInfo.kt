package com.example.harusikdan.data.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class FoodInfo(
    @SerializedName("food_carbo") val food_carbo: Double,
    @SerializedName("food_cholesterol") val food_cholesterol: Double,
    @SerializedName("food_fat") val food_fat: Double,
    @SerializedName("food_fattyacid") val food_fattyacid: Double,
    @SerializedName("food_kcal") val food_kcal: Double,
    @SerializedName("food_name") val food_name: String,
    @SerializedName("food_one_time") val food_one_time: Double,
    @SerializedName("food_protein") val food_protein: Double,
    @SerializedName("food_salt") val food_salt: Double,
    @SerializedName("food_sugar") val food_sugar: Double,
    @SerializedName("food_transfattyacid") val food_transfattyacid: Double,
    @SerializedName("id") val id: Int
)