package com.example.testingcleanarchitecture.base

import kotlinx.coroutines.*
import timber.log.Timber

abstract class BaseUseCase<out T : Any>(
    private val defaultDispatchers: CoroutineDispatcher = Dispatchers.Default,
    mainDispatchers: CoroutineDispatcher = Dispatchers.Main
) {

    protected var parentJob = SupervisorJob()
    private val localScope = CoroutineScope(mainDispatchers + parentJob)
    protected var requestParams: RequestParams = RequestParams.EMPTY

    abstract suspend fun executeOnBackground(): T

    private suspend fun executeCatchError(): Result<T> = withContext(defaultDispatchers) {
        try {
            Success(executeOnBackground())
        } catch (throwable: Throwable) {
            Fail(throwable)
        }
    }

    fun execute(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit) {
        execute(onSuccess, onError, RequestParams.EMPTY)
    }

    fun execute(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit, useCaseRequestParams: RequestParams) {
        cancelJobs()
        localScope.launchCatchError(block = {
            this.requestParams = useCaseRequestParams
            val result = executeCatchError()
            when (result) {
                is Success -> onSuccess(result.data)
                is Fail -> onError(result.throwable)
            }
        }) {
            if (it !is CancellationException)
                onError(it)
            else
                Timber.e(JobCancelationException(it.localizedMessage))
        }
    }

    fun cancelJobs() {
        localScope.coroutineContext.cancelChildren()
    }
}
