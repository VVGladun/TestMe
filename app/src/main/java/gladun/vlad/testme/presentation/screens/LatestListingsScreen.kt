package gladun.vlad.testme.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import gladun.vlad.testme.R
import gladun.vlad.testme.domain.model.Listing
import gladun.vlad.testme.domain.model.LoadingState
import gladun.vlad.testme.domain.model.ScreenLceState
import gladun.vlad.testme.presentation.ui.components.TestMeAlertDialog
import gladun.vlad.testme.presentation.ui.components.TopBarAction
import gladun.vlad.testme.presentation.ui.copyPaddings
import gladun.vlad.testme.presentation.ui.showToast
import gladun.vlad.testme.presentation.ui.theme.LocalCustomTheme
import gladun.vlad.testme.presentation.ui.theme.TestMeTheme
import gladun.vlad.testme.presentation.ui.theme.spacing
import gladun.vlad.testme.presentation.ui.toDollarsAndCents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LatestListingsScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: LatestListingsViewModel = hiltViewModel()
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LatestListingsScreenContent(
        modifier = modifier,
        uiState = state.value,
        onPullToRefresh = {
            viewModel.onPullToRefresh()
        },
        onErrorDialogRetry = {
            viewModel.onErrorDialogRetry()
        },
        onErrorDialogDismiss = {
            viewModel.onErrorDialogDismiss()
        },
        onCartClick = {
            context.showToast("TODO: Cart screen")
        },
        onSearchClick = {
            context.showToast("TODO: Search screen")
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LatestListingsScreenContent(
    modifier: Modifier = Modifier,
    uiState: ScreenLceState<List<Listing>>,
    onPullToRefresh: () -> Unit = {},
    onErrorDialogRetry: () -> Unit = {},
    onErrorDialogDismiss: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onCartClick: () -> Unit = {}
) {
    val isRefreshingOrLoading = uiState.loading !is LoadingState.None
    val content = uiState.content.orEmpty()

    val listState = rememberLazyListState()
    val pullRefreshState = rememberPullToRefreshState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

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
                        onSearchClick()
                    }
                    TopBarAction(
                        iconResId = R.drawable.ic_cart,
                        description = R.string.acc_cart_icon
                    ) {
                        onCartClick()
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { values ->
        PullToRefreshBox(
            isRefreshing = isRefreshingOrLoading,
            onRefresh = { onPullToRefresh() },
            modifier = Modifier.fillMaxSize(),
            state = pullRefreshState,
            indicator = {
                Indicator(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 64.dp),
                    isRefreshing = isRefreshingOrLoading,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    state = pullRefreshState,
                    maxDistance = 120.dp
                )
            }
        ) {
            if (content.isNotEmpty()) {
                ListContent(
                    content = content,
                    contentPadding = values.copyPaddings(bottom = 0.dp), // we already set the bottom nav bar related paddings in the host
                    listState = listState
                )
            } else if (!isRefreshingOrLoading) {
                EmptyResultScreen(onRetry = {
                    onErrorDialogRetry()
                })
            }
        }

        if (uiState.loading is LoadingState.None) {
            uiState.error?.let {
                TestMeAlertDialog(
                    dialogTitle = it.title,
                    dialogMessage = it.message,
                    primaryLabel = it.primaryActionLabel,
                    secondaryLabel = it.secondaryActionLabel,
                    onPrimaryAction = {
                        onErrorDialogRetry()
                    },
                    onSecondaryAction = {
                        onErrorDialogDismiss()
                    },
                    onDismiss = {
                        onErrorDialogDismiss()
                    }
                )
            }
        }
    }
}

@Composable
private fun ListContent(
    modifier: Modifier = Modifier,
    content: List<Listing>,
    contentPadding: PaddingValues,
    listState: LazyListState
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        state = listState
    ) {
        items(content, key = {it.listingId}) {
            ListingItem(listing = it)
        }
    }
}

@Composable
fun EmptyResultScreen(
    modifier: Modifier = Modifier,
    message: String = stringResource(R.string.empty_content_message),
    retryButtonText: String = stringResource(R.string.empty_content_retry),
    onRetry: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = LocalCustomTheme.current.textNormalEmphasis
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onRetry,
            modifier = Modifier
                .wrapContentWidth()
                .padding(8.dp),
            colors = ButtonColors(
                containerColor = LocalCustomTheme.current.actionColor,
                disabledContainerColor = Color.Gray,
                contentColor = Color.White,
                disabledContentColor = Color.White
            )
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = retryButtonText,
            )
        }
    }
}

@Composable
fun ListingItem(listing: Listing) {

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceBright)
            .wrapContentSize(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(
                    horizontal = MaterialTheme.spacing.medium,
                    vertical = MaterialTheme.spacing.small
                )
        ) {
            AsyncImage(
                model = listing.imageUrl,
                contentDescription = "Listing image for ${listing.title}",
                modifier = Modifier
                    .width(80.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(MaterialTheme.spacing.xSmall))
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.ic_launcher_foreground) //TODO: a placeholder image
            )

            Column(
                modifier = Modifier
                    .padding(start = MaterialTheme.spacing.small)
                    .fillMaxWidth()
            ) {
                if (!listing.location.isNullOrBlank()) {
                    Text(
                        text = listing.location,
                        style = MaterialTheme.typography.labelSmall,
                        color = LocalCustomTheme.current.textLowEmphasis
                    )
                }

                Text(
                    text = listing.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = LocalCustomTheme.current.textNormalEmphasis,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Start),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = LocalCustomTheme.current.textNormalEmphasis,
                            text = listing.displayPrice.orEmpty()
                        )
                        if (!listing.isClassified) {
                            Text(
                                modifier = Modifier.align(Alignment.Start),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = LocalCustomTheme.current.textLowEmphasis,
                                text = listing.reserveState.orEmpty(),
                            )
                        }
                    }

                    if (!listing.isClassified && listing.buyNowPrice != null) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.End),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = LocalCustomTheme.current.textNormalEmphasis,
                                text = listing.buyNowPrice.toDollarsAndCents()
                            )
                            Text(
                                modifier = Modifier.align(Alignment.End),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = LocalCustomTheme.current.textNormalEmphasis,
                                text = "Buy Now",
                            )
                        }
                    }

                    if (!listing.isClassified && listing.buyNowPrice != null) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.End),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = LocalCustomTheme.current.textNormalEmphasis,
                                text = listing.buyNowPrice.toDollarsAndCents()
                            )
                            Text(
                                modifier = Modifier.align(Alignment.End),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = LocalCustomTheme.current.textNormalEmphasis,
                                text = "Buy Now",
                            )
                        }
                    }
                }
            }
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray
        )
    }
}

// region Preview
@Preview(showBackground = true, name = "Loading State")
@Composable
private fun LatestListingsScreenContent_LoadingPreview() {
    TestMeTheme {
        LatestListingsScreenContent(
            uiState = ScreenLceState(
                loading = LoadingState.Refreshing
            )
        )
    }
}

@Preview(showBackground = true, name = "Empty State")
@Composable
private fun LatestListingsScreenContent_EmptyPreview() {
    TestMeTheme {
        LatestListingsScreenContent(
            uiState = ScreenLceState(
                loading = LoadingState.None,
                content = emptyList()
            )
        )
    }
}

@Preview(showBackground = true, name = "With Content")
@Composable
private fun LatestListingsScreenContent_WithContentPreview() {
    TestMeTheme {
        LatestListingsScreenContent(
            uiState = ScreenLceState(
                loading = LoadingState.None,
                content = listOf(
                    Listing(
                        listingId = "test01",
                        title = "Some title",
                        location = "Christchurch",
                        imageUrl = "some url",
                        buyNowPrice = 5014.89,
                        displayPrice = "$1234.87",
                        isClassified = false,
                        reserveState = "Reserve Met"
                    )
                )
            )
        )
    }
}
// endregion