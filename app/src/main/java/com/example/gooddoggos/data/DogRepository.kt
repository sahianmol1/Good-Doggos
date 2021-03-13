package com.example.gooddoggos.data

import androidx.paging.*
import com.example.gooddoggos.api.DogApi
import com.example.gooddoggos.api.DogApi.Companion.PAGE_SIZE
import com.example.gooddoggos.data.local.DogDatabase
import com.example.gooddoggos.data.network.DogPagingSource
import com.example.gooddoggos.data.network.DogRemoteMediator
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class DogRepository @Inject constructor(private val dogApi: DogApi, private val dogDatabase: DogDatabase) {

    fun getAllDogs() = Pager(
            config = PagingConfig(
                    pageSize = PAGE_SIZE,
                    enablePlaceholders = false
            ),
            remoteMediator = DogRemoteMediator(dogApi, dogDatabase),
            pagingSourceFactory = { dogDatabase.getDogDao().getAllDogs() }
    ).liveData
}