package gladun.vlad.testme.data.repository

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
    @IoDispatcher private val backgroundDispatcher: CoroutineDispatcher
) : LatestListingRepository {

    override fun getLatestListings(): Flow<List<Listing>> {
        // TODO: retrofit service call
        return flow {
            emit(
                listOf(
                    Listing(
                        listingId = "test01",
                        title = "Some title",
                        location = "Christchurch",
                        imageUrl = "picture_url",
                        buyNowPrice = 5014.89,
                        displayPrice = "$1234.87",
                        isClassified = false,
                        reserveState = "Reserve Met"
                    )
                )
            )
        }.flowOn(backgroundDispatcher)
    }
}