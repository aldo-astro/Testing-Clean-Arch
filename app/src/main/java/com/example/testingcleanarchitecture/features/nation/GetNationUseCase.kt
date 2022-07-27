package com.example.testingcleanarchitecture.features.nation

import com.example.testingcleanarchitecture.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetNationUseCase(
    private val repository: NationRepository,
    private val dispatchers: CoroutineDispatcher = Dispatchers.IO
) : BaseUseCase<Flow<Nation>>() {

    private var name: String = ""

    override suspend fun executeOnBackground(): Flow<Nation> {
        return repository.getNation(name).flowOn(dispatchers)
    }

    operator fun invoke(name: String): GetNationUseCase {
        this.name = name
        return this
    }
}
