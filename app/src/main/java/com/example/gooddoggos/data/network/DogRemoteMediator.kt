package com.example.gooddoggos.data.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.gooddoggos.api.DogApi
import com.example.gooddoggos.data.local.DogDatabase
import com.example.gooddoggos.models.GoodDoggoItem
import com.example.gooddoggos.models.RemoteKeys
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class DogRemoteMediator(private val dogApi: DogApi, private val dogDatabase: DogDatabase) : RemoteMediator<Int, GoodDoggoItem>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, GoodDoggoItem>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> DOGS_STARTING_PAGE_INDEX
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                if (remoteKey?.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKey.nextKey
            }
        }

        try {
            val dogs = dogApi.getAllDogs(page = page)
            val endOfPaginationReached = dogs.isEmpty()

            dogDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dogDatabase.getKeysDao().clearRemoteKeys()
                    dogDatabase.getDogDao().clearDogs()
                }

                val prevKey = if (page == DOGS_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val keys = dogs.map {
                    RemoteKeys(dogId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                dogDatabase.getKeysDao().insertAllKeys(keys)
                dogDatabase.getDogDao().saveAllDogs(dogs)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)


        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, GoodDoggoItem>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
                ?.let { dog ->
                    // Get the remote keys of the last item retrieved
                    dogDatabase.getKeysDao().getRemoteKeysDogId(dog.id)
                }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, GoodDoggoItem>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
                ?.let { dog ->
                    // Get the remote keys of the first items retrieved
                    dogDatabase.getKeysDao().getRemoteKeysDogId(dog.id)
                }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
            state: PagingState<Int, GoodDoggoItem>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { dogId ->
                dogDatabase.getKeysDao().getRemoteKeysDogId(dogId)
            }
        }
    }

//    override suspend fun initialize(): InitializeAction {
//        return InitializeAction.SKIP_INITIAL_REFRESH
//    }
}