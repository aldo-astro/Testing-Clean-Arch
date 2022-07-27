package com.example.testingcleanarchitecture.features.character

import com.example.testingcleanarchitecture.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetCharacterListUseCase(private val repository: CharacterRepository) :
    BaseUseCase<Flow<CharacterListData>>() {

    override suspend fun executeOnBackground(): Flow<CharacterListData> {
        val result = repository.getCharacterList()
        return flowOf(result)
    }
}
