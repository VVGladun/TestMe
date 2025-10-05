package gladun.vlad.testme.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import gladun.vlad.testme.presentation.screens.LatestListingsScreen
import gladun.vlad.testme.presentation.screens.MyTradeMeScreen
import gladun.vlad.testme.presentation.screens.WatchlistScreen

@Composable
fun HomeNavGraph(
    modifier: Modifier = Modifier,
    navigateTo: (NavScreen) -> Unit,
    homeNavController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = homeNavController,
        startDestination = NavScreen.HomeDestination.LatestListings
    ) {
        composable<NavScreen.HomeDestination.LatestListings> {
            LatestListingsScreen()
        }
        composable<NavScreen.HomeDestination.Watchlist> {
            WatchlistScreen()
        }
        composable<NavScreen.HomeDestination.MyTradeMe> {
            MyTradeMeScreen()
        }
    }
}