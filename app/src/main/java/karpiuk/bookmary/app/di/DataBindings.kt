package karpiuk.bookmary.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import karpiuk.bookmary.data.di.DaggerSummaryDataComponent
import karpiuk.bookmary.domain.repositories.BookRepository

@Module
@InstallIn(SingletonComponent::class)
object DataBindings {

    private val component = DaggerSummaryDataComponent.factory().create()

    @Provides
    fun provideBookRepository(): BookRepository =
        component.bookRepository()
}