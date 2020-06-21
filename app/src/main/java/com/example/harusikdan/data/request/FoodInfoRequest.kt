package com.example.harusikdan.data.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class FoodInfoRequest (
    @SerializedName("food_name") var food_name: String
)