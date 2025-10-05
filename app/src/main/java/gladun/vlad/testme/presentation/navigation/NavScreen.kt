package gladun.vlad.testme.presentation.navigation

import kotlinx.serialization.Serializable

/**
 * The nav destinations and their input params (if needed)
 */
sealed class NavScreen {

    @Serializable
    data object Home: NavScreen()

    sealed class HomeDestination: NavScreen() {
        @Serializable
        data object LatestListings: HomeDestination()

        @Serializable
        data object Watchlist: HomeDestination()

        @Serializable
        data object MyTradeMe: HomeDestination()
    }

}