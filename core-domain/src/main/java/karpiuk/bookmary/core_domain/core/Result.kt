package karpiuk.bookmary.core_domain.core

sealed class Result<out T> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val throwable: Throwable): Result<Nothing>()
    data object Loading: Result<Nothing>()
}