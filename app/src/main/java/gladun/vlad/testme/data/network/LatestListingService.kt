package gladun.vlad.testme.data.network

import gladun.vlad.testme.data.model.LatestListingResponseDTO
import retrofit2.http.GET

interface LatestListingService {

    @GET("v1/listings/latest.json")
    suspend fun getListings(): LatestListingResponseDTO

}