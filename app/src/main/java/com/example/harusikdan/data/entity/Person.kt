package com.example.harusikdan.data.entity

import java.util.*
import kotlin.collections.ArrayList

object Person {
    var weight = 0.0
    var height = 0.0
    var gender //4th fragment
            = 0
    var age = 0
    var activity //5th fragment
            = 0
    var targetCalorie
            = 0.0
    var diseaseList: ArrayList<Int> = ArrayList()


    var preferredList: ArrayList<String> = ArrayList()
    var nonPreferredList: ArrayList<String> = ArrayList()


    fun setTargetCalorie()  {
        targetCalorie =  ((height - 100) * 0.9) * activity
    }


    override fun toString(): String {
        return "몸무게 : $weight , 키 : $height, 나이 : $age, 성별: $gender, 활동성: $activity, 질병정보: $diseaseList, " +
                "선호음식 리스트 : $preferredList, 비선호음식 리스트 : $nonPreferredList"
    }

}