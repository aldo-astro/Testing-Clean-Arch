package com.example.testingcleanarchitecture.features.nation

import kotlinx.coroutines.flow.Flow

interface NationRepository {

    suspend fun getNation(name: String): Flow<Nation>
}
