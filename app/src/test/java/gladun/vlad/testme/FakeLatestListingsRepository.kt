package gladun.vlad.testme

import gladun.vlad.testme.domain.model.Listing
import gladun.vlad.testme.domain.repository.LatestListingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@ExperimentalCoroutinesApi
class FakeLatestListingRepository(var items: List<Listing> = emptyList(), val dispatcher: CoroutineDispatcher) : LatestListingRepository {
    var shouldThrowError: Exception? = null

    override fun getLatestListings(): Flow<List<Listing>> {
        return flow {
            delay(100)
            shouldThrowError?.let {
                throw it
            }
            emit(items)
        }.flowOn(dispatcher)
    }
}