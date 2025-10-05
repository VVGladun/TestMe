package gladun.vlad.testme.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import gladun.vlad.testme.presentation.screens.HomeScreen

/**
 * The main Nav host/graph that may include the login/auth screen,
 * then the home screen (which is going to be a nested graph),
 * and then all the other screens that needs to be rendered outside of the home screen nav host,
 * e.g. an in-app browser screen that needs to be displayed on top of the tabbed home screen
 */
@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavScreen.Home
    ) {


        composable<NavScreen.Home> {
            HomeScreen { navScreen ->
                navController.navigate(navScreen)
            }
        }
    }
}