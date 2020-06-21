package com.example.harusikdan.data.entity


import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*


open class FoodVO (
    @PrimaryKey var id: Long = 0,
    var foodName: String = "",
    var mealTime: String = "",
    var date: String = ""
): RealmObject()