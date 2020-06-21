package com.example.harusikdan.data.entity


import androidx.databinding.OnRebindCallback
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*


open class FoodVO (
    @PrimaryKey var id: Long = 0,
    var foodName: String = "",
    var mealTime: String = "",
    var date: String = "",
    var calorie: Double = 0.0,
    var carbohydrate: Double = 0.0,
    var protein: Double = 0.0,
    var fat: Double = 0.0,
    var salt: Double = 0.0,
    var sugar: Double = 0.0
): RealmObject()