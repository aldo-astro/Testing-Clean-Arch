package com.example.testingcleanarchitecture

import com.example.testingcleanarchitecture.features.character.CharacterListData
import com.example.testingcleanarchitecture.features.character.CharacterRepository
import com.example.testingcleanarchitecture.features.character.GetCharacterListUseCase
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test

// TODO: Will be used for parallel API call
class GetCharacterListUseCaseTest {

    @Test
    fun `get character list success`(): Unit = runBlocking {
        // Given
        val repo = object : CharacterRepository {
            override suspend fun getCharacterList(): CharacterListData {
                return listOf("Albedo", "Diluc")
            }
        }
        val useCase = GetCharacterListUseCase(repo)

        // When
        val result = useCase.executeOnBackground().first()

        // Then
        assertTrue(result.isNotEmpty())
    }
}
