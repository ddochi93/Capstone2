package com.example.harusikdan.feature.takemenu

import android.graphics.Point
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class RectPos(
    var leftTop: Point? = null,
    var rightBottom: Point? = null,
    var color: Int?= 0
): Parcelable
