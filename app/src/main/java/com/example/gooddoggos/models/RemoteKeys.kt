package com.example.gooddoggos.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keys_table")
data class RemoteKeys (
        @PrimaryKey
        val dogId: String,
        val prevKey: Int?,
        val nextKey: Int?
)