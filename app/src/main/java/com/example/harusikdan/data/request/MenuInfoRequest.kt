package com.example.harusikdan.data.request

import com.google.gson.annotations.SerializedName

data class MenuInfoRequest (
    @SerializedName("text") val text : ArrayList<String>
)