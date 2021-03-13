package com.example.gooddoggos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.gooddoggos.models.GoodDoggoItem
import com.example.gooddoggos.models.RemoteKeys
import com.example.gooddoggos.typeConverters.BreedTypeConverter

@Database(
        entities = [GoodDoggoItem::class, RemoteKeys::class],
        version = 1
)
@TypeConverters(BreedTypeConverter::class)
abstract class DogDatabase: RoomDatabase() {

    abstract fun getDogDao(): DogDao
    abstract fun getKeysDao(): RemoteKeysDao
}