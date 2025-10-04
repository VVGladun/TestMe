package gladun.vlad.testme.domain.model

/**
 * Domain model for the listing list item
 */
data class Listing(
    val listingId: String,
    val title: String,
    val location: String?,
    val imageUrl: String?,
    val buyNowPrice: Double?,
    val displayPrice: String?,
    val isClassified: Boolean,
    val reserveState: String?
)
