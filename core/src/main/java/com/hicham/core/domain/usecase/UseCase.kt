package com.hicham.core.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class UseCase<in PARAM, out RESULT>(
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) {

    protected abstract suspend fun run(param: PARAM): RESULT

    suspend operator fun invoke(param: PARAM) = withContext(coroutineContext) { run(param) }
}