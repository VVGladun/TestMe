package gladun.vlad.testme.data.mappers

import gladun.vlad.testme.data.model.ListingDTO
import gladun.vlad.testme.data.model.ReserveState
import gladun.vlad.testme.domain.model.Listing

fun ListingDTO.toDomainModel(): Listing? {
    if (this.title.isNullOrBlank()) {
        return null
    }
    return Listing(
        listingId = this.listingId.toString(),
        title = this.title,
        location = this.region ,
        imageUrl = this.pictureHref,
        buyNowPrice = this.buyNowPrice,
        displayPrice = this.priceDisplay.orEmpty(),
        isClassified = this.isClassified == true,
        reserveState = when (this.reserveState) {
            ReserveState.NONE -> "No Reserve"
            ReserveState.MET -> "Reserve Met"
            ReserveState.NOT_MET -> "Reserve Not Met"
            else -> null
        }
    )
}