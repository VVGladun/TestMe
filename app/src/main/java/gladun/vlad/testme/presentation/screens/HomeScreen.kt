package gladun.vlad.testme.presentation.screens

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import gladun.vlad.testme.R
import gladun.vlad.testme.presentation.navigation.HomeNavGraph
import gladun.vlad.testme.presentation.navigation.NavScreen
import gladun.vlad.testme.presentation.ui.theme.LocalCustomTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigateTo: (NavScreen) -> Unit //Navigate outside of the nested home graph (i.e. render screen on top of the tabbed home screen with all its nested screens)
) {
    //to navigate between the tabs and (if required later) build nested hierarchy, e.g. latest listings -> listing details, within the home screen while keeping the host's bottom nav bar
    val homeNavController = rememberNavController()
    val homeNavBackStackEntry by homeNavController.currentBackStackEntryAsState()

    val bottomBarTabs = listOf<HomeBottomBarTab>(
        HomeBottomBarTab(
            title = stringResource(R.string.tab_latest_listings),
            iconResId = R.drawable.ic_search,
            destination = NavScreen.HomeDestination.LatestListings
        ),
        HomeBottomBarTab(
            title = stringResource(R.string.tab_watchlist),
            iconResId = R.drawable.ic_binoculars,
            destination = NavScreen.HomeDestination.Watchlist
        ),
        HomeBottomBarTab(
            title = stringResource(R.string.tab_my_trade_me),
            iconResId = R.drawable.ic_profile,
            destination = NavScreen.HomeDestination.MyTradeMe
        ),
    )

    val bottomNavBarItemColors: NavigationBarItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = LocalCustomTheme.current.selectedTabTextColor,
        selectedTextColor = LocalCustomTheme.current.selectedTabTextColor,
        unselectedIconColor = LocalCustomTheme.current.unselectedTabTextColor,
        unselectedTextColor = LocalCustomTheme.current.unselectedTabTextColor
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                bottomBarTabs.forEach { tab ->
                    NavigationBarItem(
                        selected = (homeNavBackStackEntry?.destination?.route ?: NavScreen.HomeDestination.LatestListings::class.qualifiedName) == tab.destination::class.qualifiedName,
                        onClick = {
                            homeNavController.navigate(route = tab.destination) {
                                popUpTo(homeNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(tab.iconResId),
                                contentDescription = tab.title,
                            )
                        },
                        label = {
                            Text(tab.title)
                        },
                        colors = bottomNavBarItemColors
                    )
                }
            }
        }
    ) { values ->
        Box(
            modifier = Modifier.fillMaxSize().padding(bottom = values.calculateBottomPadding())
        ) {
            HomeNavGraph(
                navigateTo = navigateTo,
                homeNavController = homeNavController
            )
        }
    }
}

data class HomeBottomBarTab(
    val title: String,
    @DrawableRes val iconResId: Int,
    val destination: NavScreen.HomeDestination,
)