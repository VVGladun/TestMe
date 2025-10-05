package gladun.vlad.testme.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gladun.vlad.testme.data.repository.LatestListingRepositoryImpl
import gladun.vlad.testme.domain.repository.LatestListingRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindListingRepositoryRepository(
        latestListingRepositoryImpl: LatestListingRepositoryImpl
    ): LatestListingRepository
}