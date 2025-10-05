package gladun.vlad.testme.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LatestListingResponseDTO(
    @Json(name = "TotalCount")
    val totalCount: Int,

    @Json(name = "Page")
    val page: Int,

    @Json(name = "PageSize")
    val pageSize: Int,

    @Json(name = "List")
    val list: List<ListingDTO>? = null,
)

@JsonClass(generateAdapter = true)
data class ListingDTO(
    @Json(name = "ListingId")
    val listingId: Long,

    @Json(name = "Title")
    val title: String? = null,

    @Json(name = "Category")
    val category: String? = null,

    @Json(name = "PriceDisplay")
    val priceDisplay: String?,

    @Json(name = "StartPrice")
    val startPrice: Double?,

    @Json(name = "BuyNowPrice")
    val buyNowPrice: Double?,

    @Json(name = "PictureHref")
    val pictureHref: String? = null,

    @Json(name = "Region")
    val region: String? = null,

    @Json(name = "Suburb")
    val suburb: String? = null,

    @Json(name = "BidCount")
    val bidCount: Int?,

    @Json(name = "IsReserveMet")
    val isReserveMet: Boolean?,

    @Json(name = "HasReserve")
    val hasReserve: Boolean?,

    @Json(name = "HasBuyNow")
    val hasBuyNow: Boolean?,

    @Json(name = "ReserveState")
    val reserveState: ReserveState?,

    @Json(name = "IsClassified")
    val isClassified: Boolean? = false,

    @Json(name = "Subtitle")
    val subtitle: String? = null,

    @Json(name = "IsBuyNowOnly")
    val isBuyNowOnly: Boolean? = false
)

enum class ReserveState(val value: Int) {
    NONE(0),

    MET(1),

    NOT_MET(2),

    NOT_APPLICABLE(3);

    companion object {
        fun fromValue(value: Int): ReserveState {
            return values().find { it.value == value } ?: NONE
        }
    }
}
