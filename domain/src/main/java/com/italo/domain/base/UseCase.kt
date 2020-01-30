package com.italo.domain.base

import kotlinx.coroutines.*

abstract class UseCase<out Type, in Params> {

    private var job: Deferred<OneOf<Failure, Type>>? = null

    abstract suspend fun run(params: Params): OneOf<Failure, Type>

    fun execute(params: Params, onResult: (OneOf<Failure, Type>) -> Unit) {
        job?.cancel()
        job = GlobalScope.async(Dispatchers.IO) { run(params) }
        GlobalScope.launch(Dispatchers.Main) {
            val result = job!!.await()
            onResult(result)
        }
    }

    open fun cancel() {
        job?.cancel()
    }

    open class NoParams
}