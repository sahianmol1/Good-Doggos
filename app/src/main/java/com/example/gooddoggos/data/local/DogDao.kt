package com.example.gooddoggos.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gooddoggos.models.GoodDoggoItem

@Dao
interface DogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllDogs(dogs: List<GoodDoggoItem>)

    @Query("SELECT * FROM dog_table")
    fun getAllDogs(): PagingSource<Int, GoodDoggoItem>

    @Query("DELETE FROM dog_table")
    suspend fun clearDogs()
}