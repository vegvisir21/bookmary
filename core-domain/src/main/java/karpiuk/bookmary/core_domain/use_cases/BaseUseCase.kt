package karpiuk.bookmary.core_domain.use_cases

interface BaseUseCase<In, Out> {
    suspend fun execute(params: In): Out
}