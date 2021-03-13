package com.example.gooddoggos.di

import android.content.Context
import androidx.room.Room
import com.example.gooddoggos.api.DogApi
import com.example.gooddoggos.api.DogApi.Companion.BASE_URL
import com.example.gooddoggos.data.local.DogDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providesDogApi(retrofit: Retrofit) = retrofit.create(DogApi::class.java)

    @Provides
    @Singleton
    fun provideDogDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context, DogDatabase::class.java, "dog_db").build()

    @Provides
    @Singleton
    fun provideDogDao(db: DogDatabase) = db.getDogDao()

    @Provides
    @Singleton
    fun provideKeysDao(db: DogDatabase) = db.getKeysDao()
}