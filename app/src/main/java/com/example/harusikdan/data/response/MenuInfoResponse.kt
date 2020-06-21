package com.example.harusikdan.data.response

import com.google.gson.annotations.SerializedName

data class MenuInfoResponse (
    @SerializedName("menu_result") val menu_result : List<List<Int>>
)