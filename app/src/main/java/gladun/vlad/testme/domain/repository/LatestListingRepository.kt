package gladun.vlad.testme.domain.repository

import gladun.vlad.testme.domain.model.Listing
import kotlinx.coroutines.flow.Flow

interface LatestListingRepository {
    fun getLatestListings(): Flow<List<Listing>>
}