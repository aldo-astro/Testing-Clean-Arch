package com.example.testingcleanarchitecture.features.character

interface CharacterRepository {

    suspend fun getCharacterList(): CharacterListData
}
