package com.example.gooddoggos.typeConverters

import androidx.room.TypeConverter
import com.example.gooddoggos.models.Breed

class BreedTypeConverter {

    @TypeConverter
    fun stringToBreed(value: List<String?>?): List<Breed?>? {
        val breedList = mutableListOf<Breed>()
        if (value != null) {
            for(breedName in value) {
                breedList.add(Breed(name = breedName))
            }
        }
        return breedList
    }

    @TypeConverter
    fun breedToString(breeds: List<Breed?>?): List<String?>? {
        val breedList = mutableListOf<String>()

        if (breeds != null) {
            for (breed in breeds) {
                if (breed != null) {
                    breedList.add(breed.name.toString())
                }
            }
        }

        return breedList
    }
}