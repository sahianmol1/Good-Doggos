package com.example.gooddoggos.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gooddoggos.api.DogApi
import com.example.gooddoggos.models.GoodDoggo
import com.example.gooddoggos.models.GoodDoggoItem
import retrofit2.HttpException
import java.io.IOException

const val DOGS_STARTING_PAGE_INDEX = 0

class DogPagingSource(private val dogApi: DogApi): PagingSource<Int, GoodDoggoItem>() {
    override fun getRefreshKey(state: PagingState<Int, GoodDoggoItem>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GoodDoggoItem> {
       val position = params.key ?: DOGS_STARTING_PAGE_INDEX

        return try {
            val response = dogApi.getAllDogs(page = position)
            LoadResult.Page(
                data = response,
                prevKey = if (position == DOGS_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}