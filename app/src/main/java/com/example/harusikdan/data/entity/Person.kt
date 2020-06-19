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
    var targetCalorie //이건 서버에서 자동 설정
            = 0
    var diseaseList: ArrayList<Int> = ArrayList()


    var preferredList: ArrayList<String> = ArrayList()
    var nonPreferredList: ArrayList<String> = ArrayList()




    override fun toString(): String {
        return "몸무게 : $weight , 키 : $height, 나이 : $age, 성별: $gender, 활동성: $activity, 질병정보: $diseaseList, " +
                "선호음식 리스트 : $preferredList, 비선호음식 리스트 : $nonPreferredList"
    }

}