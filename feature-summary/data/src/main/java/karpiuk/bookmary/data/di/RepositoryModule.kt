package karpiuk.bookmary.data.di

import dagger.Binds
import dagger.Module
import karpiuk.bookmary.data.repositories.BookRepositoryImpl
import karpiuk.bookmary.domain.repositories.BookRepository

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindBookRepository(
        repository: BookRepositoryImpl
    ): BookRepository

}