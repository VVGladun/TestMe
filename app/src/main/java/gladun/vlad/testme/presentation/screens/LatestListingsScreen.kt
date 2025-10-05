package gladun.vlad.testme.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import gladun.vlad.testme.R
import gladun.vlad.testme.domain.model.Listing
import gladun.vlad.testme.presentation.ui.components.TopBarAction
import gladun.vlad.testme.presentation.ui.copyPaddings
import gladun.vlad.testme.presentation.ui.showToast

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LatestListingsScreen(
    modifier: Modifier = Modifier
) {
    val latestListingsViewModel: LatestListingsViewModel = hiltViewModel()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val context = LocalContext.current
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.screen_title_latest))
                },
                actions = {
                    TopBarAction(
                        iconResId = R.drawable.ic_search,
                        description = R.string.acc_search_icon
                    ) {
                        context.showToast("TODO: Search screen")
                    }
                    TopBarAction(
                        iconResId = R.drawable.ic_cart,
                        description = R.string.acc_cart_icon
                    ) {
                        context.showToast("TODO: Cart screen")
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { values ->
        LazyColumn (
            modifier = Modifier.fillMaxSize(),
            contentPadding = values.copyPaddings(bottom = 0.dp) // we already set the bottom nav bar related paddings in the host
        ) {
            item {
                Text("TODO: Latest Listings")
            }
        }
    }
}

@Composable
fun ListingItem(
    listing: Listing
) {
    //TODO: lazy column item
}