package com.example.gooddoggos.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gooddoggos.models.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKeys(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM keys_table")
    suspend fun getAllDoggoKeys(): List<RemoteKeys?>?

    @Query("SELECT * FROM keys_table WHERE dogId = :dogId")
    suspend fun getRemoteKeysDogId(dogId: String): RemoteKeys?

    @Query("DELETE FROM keys_table")
    suspend fun clearRemoteKeys()
}