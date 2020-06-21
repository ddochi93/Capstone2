package com.example.harusikdan.data.request

import com.fasterxml.jackson.annotation.JsonProperty

data class FoodInfoRequest (
    @JsonProperty("food_name") var food_name: String
)