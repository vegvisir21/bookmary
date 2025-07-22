package karpiuk.bookmary.core_domain.use_cases

import kotlinx.coroutines.flow.Flow

interface FlowUseCase<In, Out> {
    suspend fun execute(params: In): Flow<Out>
}