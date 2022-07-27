package com.example.testingcleanarchitecture

import com.example.testingcleanarchitecture.features.nation.GetNationUseCase
import com.example.testingcleanarchitecture.features.nation.Nation
import com.example.testingcleanarchitecture.features.nation.NationRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.concurrent.TimeoutException

class GetNationUseCaseTest {

    @Test
    fun `get nation - success`(): Unit = runBlocking {
        // Given
        val repository = object : NationRepository {
            override suspend fun getNation(name: String): Flow<Nation> {
                return flowOf(
                    Nation(
                        "Mondstadt",
                        "Barbatos",
                        "Anemo",
                        "Knight of Favonius"
                    )
                )
            }
        }
        val useCase = GetNationUseCase(repository)

        // When
        val result = useCase("Mondstadt").executeOnBackground().first()

        // Then
        assertEquals(result.name, "Mondstadt")
        assertEquals(result.archon, "Barbatos")
    }

    @Test
    fun `get nation - fail`(): Unit = runBlocking {
        // Given
        val repository = object : NationRepository {
            override suspend fun getNation(name: String): Flow<Nation> {
                return flow {
                    throw TimeoutException()
                }
            }
        }
        val useCase = GetNationUseCase(repository)

        try {
            // When
            useCase("Mondstadt").executeOnBackground().first()
        } catch (exception: Exception) {
            // Then
            assertTrue(exception is TimeoutException)
        }
    }
}
