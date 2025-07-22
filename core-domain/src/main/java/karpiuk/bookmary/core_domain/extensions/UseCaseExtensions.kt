package karpiuk.bookmary.core_domain.extensions

import karpiuk.bookmary.core_domain.core.Result
import karpiuk.bookmary.core_domain.use_cases.BaseUseCase
import karpiuk.bookmary.core_domain.use_cases.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

suspend fun <Out> BaseUseCase<Unit, Out>.execute(): Out {
    return execute(Unit)
}

suspend fun <Out> FlowUseCase<Unit, Out>.execute(): Flow<Out> {
    return execute(Unit)
}

suspend fun <In, Out> FlowUseCase<In, Out>.result(params: In): Flow<Result<Out>> {
    return execute(params)
        .map<Out, Result<Out>> { Result.Success(it) }
        .onStart { emit(Result.Loading) }
        .catch { emit(Result.Error(it)) }
}

suspend fun <Out> FlowUseCase<Unit, Out>.result(): Flow<Result<Out>> {
    return execute(Unit)
        .map<Out, Result<Out>> { Result.Success(it) }
        .onStart { emit(Result.Loading) }
        .catch { emit(Result.Error(it)) }
}