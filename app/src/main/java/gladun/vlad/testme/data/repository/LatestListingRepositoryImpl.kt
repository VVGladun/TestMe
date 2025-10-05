package gladun.vlad.testme.data.repository

import gladun.vlad.testme.data.mappers.toDomainModel
import gladun.vlad.testme.data.network.LatestListingService
import gladun.vlad.testme.di.IoDispatcher
import gladun.vlad.testme.domain.model.Listing
import gladun.vlad.testme.domain.repository.LatestListingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LatestListingRepositoryImpl @Inject constructor(
    private val latestListingService: LatestListingService,
    @IoDispatcher private val backgroundDispatcher: CoroutineDispatcher
) : LatestListingRepository {

    companion object {
        private const val LATEST_LISTINGS_LIMIT = 20
    }

    override fun getLatestListings(): Flow<List<Listing>> {
        return flow {
            emit(
                latestListingService.getListings().list.orEmpty()
                    .mapNotNull {
                        it.toDomainModel()
                    }.take(LATEST_LISTINGS_LIMIT)
            )
        }.flowOn(backgroundDispatcher)
    }
}