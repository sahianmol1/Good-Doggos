package com.example.gooddoggos.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Entity(tableName = "dog_table")
data class GoodDoggoItem(
        val height: Int,
        @PrimaryKey
        val id: String,
        val url: String,
        val width: Int
)  {

}