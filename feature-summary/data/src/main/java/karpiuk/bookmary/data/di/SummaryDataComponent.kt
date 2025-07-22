package karpiuk.bookmary.data.di

import dagger.Component
import karpiuk.bookmary.domain.repositories.BookRepository

@Component(modules = [RepositoryModule::class])
interface SummaryDataComponent {
    fun bookRepository(): BookRepository

    @Component.Factory
    interface Factory {
        fun create(): SummaryDataComponent
    }
}