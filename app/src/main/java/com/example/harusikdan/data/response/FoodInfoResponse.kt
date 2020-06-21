package com.example.harusikdan.data.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class FoodInfoResponse(

    @SerializedName("foodinfo") val foodinfo: FoodInfo

)