package gladun.vlad.testme.presentation.screens

import app.cash.turbine.test
import gladun.vlad.testme.FakeLatestListingRepository
import gladun.vlad.testme.MainDispatcherRule
import gladun.vlad.testme.R
import gladun.vlad.testme.domain.model.Listing
import gladun.vlad.testme.domain.model.LoadingState
import gladun.vlad.testme.domain.model.UiText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.SocketTimeoutException
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class LatestListingsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: LatestListingsViewModel
    private lateinit var fakeRepo: FakeLatestListingRepository

    @Before
    fun setUp() {
        fakeRepo = FakeLatestListingRepository(emptyList(), mainDispatcherRule.testDispatcher)
        viewModel = LatestListingsViewModel(
            latestListingRepository = fakeRepo
        )
    }

    @Test
    fun `init - when data loads successfully, state contains content and loading is false`() = runTest {
        // Given
        val testData = testData()
        val testException: Exception? = null
        fakeRepo.apply {
            items = testData
            shouldThrowError = testException
        }

        // When
        // Then
        viewModel.uiState.test {
            // initial
            val initialState = awaitItem()
            assertEquals(LoadingState.Loading, initialState.loading)
            assertEquals(emptyList<Listing>(), initialState.content)

            // refresh completed
            val updateContentState = awaitItem()
            assertEquals(LoadingState.None, updateContentState.loading)
            assertEquals(testData, updateContentState.content)
            assertNull(updateContentState.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `init - when data loading fails, state contains initial content, no loading and error to display`() = runTest {
        // Given
        val testData = testData()
        val testException: Exception = SocketTimeoutException()
        fakeRepo.apply {
            items = testData
            shouldThrowError = testException
        }

        // When
        // Then
        viewModel.uiState.test {
            val initialState = awaitItem()
            assertEquals(LoadingState.Loading, initialState.loading)
            assertEquals(emptyList<Listing>(), initialState.content)

            val finalState = awaitItem()
            assertEquals(LoadingState.None, finalState.loading)
            assertEquals(emptyList<Listing>(), initialState.content)
            assertEquals(R.string.error_generic_title,  (finalState.error!!.title as? UiText.StringResource)!!.resId)
            assertEquals(R.string.error_generic_message,  (finalState.error.message as? UiText.StringResource)!!.resId)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onPullToRefresh - successfully refreshes the data after error`() = runTest {
        // Given
        val testData = testData()
        val testException: Exception = SocketTimeoutException()
        fakeRepo.apply {
            shouldThrowError = testException
        }

        viewModel.uiState.test {
            val initialState = awaitItem()
            assertEquals(LoadingState.Loading, initialState.loading)
            assertEquals(emptyList<Listing>(), initialState.content)
            val finalState = awaitItem()
            assertEquals(LoadingState.None, finalState.loading)
            assertEquals(emptyList<Listing>(), initialState.content)
            assertEquals(R.string.error_generic_title,  (finalState.error!!.title as? UiText.StringResource)!!.resId)
            assertEquals(R.string.error_generic_message,  (finalState.error.message as? UiText.StringResource)!!.resId)
        }

        fakeRepo.shouldThrowError = null
        fakeRepo.items = testData

        // When
        viewModel.onPullToRefresh()

        viewModel.uiState.test {
            val refreshingState = awaitItem()
            assertEquals(LoadingState.Refreshing, refreshingState.loading)
            assertEquals(emptyList<Listing>(), refreshingState.content)

            val finalState = awaitItem()
            assertEquals(LoadingState.None, finalState.loading)
            assertEquals(testData, finalState.content)
            assertNull(finalState.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onErrorDialogRetry - refreshes the data with refreshing indicator`() = runTest {
        // Given
        val testData = testData()
        val testException: Exception? = null
        fakeRepo.apply {
            items = testData
            shouldThrowError = testException
        }
        viewModel.uiState.test {
            // initial
            val initialState = awaitItem()
            assertEquals(LoadingState.Loading, initialState.loading)
            assertEquals(emptyList<Listing>(), initialState.content)

            // initial load finished completed
            val updateContentState = awaitItem()
            assertEquals(LoadingState.None, updateContentState.loading)
            assertEquals(testData, updateContentState.content)
            assertNull(updateContentState.error)
        }

        // When
        val newData = updatedData()
        fakeRepo.items = newData
        viewModel.onPullToRefresh()

        // Then
        viewModel.uiState.test {
            // initial
            val initialState = awaitItem()
            assertEquals(LoadingState.Refreshing, initialState.loading)
            assertEquals(testData, initialState.content)

            // initial load finished completed
            val updateContentState = awaitItem()
            assertEquals(LoadingState.None, updateContentState.loading)
            assertEquals(newData, updateContentState.content!!)
            assertNull(updateContentState.error)

            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `onErrorDialogDismiss - closes error dialog without refreshing`() = runTest {
        // Given
        val testData = testData()
        val testException: Exception = SocketTimeoutException()
        fakeRepo.shouldThrowError = testException

        viewModel.uiState.test {
            val initialState = awaitItem()
            assertEquals(LoadingState.Loading, initialState.loading)
            assertEquals(emptyList<Listing>(), initialState.content)

            val finalState = awaitItem()
            assertEquals(LoadingState.None, finalState.loading)
            assertEquals(emptyList<Listing>(), initialState.content)
            assertEquals(R.string.error_generic_title,  (finalState.error!!.title as? UiText.StringResource)!!.resId)
            assertEquals(R.string.error_generic_message,  (finalState.error.message as? UiText.StringResource)!!.resId)
        }

        fakeRepo.shouldThrowError = null
        fakeRepo.items = testData

        // When
        viewModel.onErrorDialogDismiss()

        viewModel.uiState.test {
            val initialState = awaitItem()
            assertEquals(LoadingState.None, initialState.loading)
            assertEquals(emptyList<Listing>(), initialState.content)
            assertNull(initialState.error)

            expectNoEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun testData(): List<Listing> {
        return listOf(testListing1)
    }

    private fun updatedData(): List<Listing> {
        return listOf(testListing1, testListing2)
    }

    private val testListing1 = Listing(
        listingId = "test01",
        title = "Some title",
        location = "Christchurch",
        imageUrl = "picture_url",
        buyNowPrice = 5014.89,
        displayPrice = "$1234.87",
        isClassified = false,
        reserveState = "Reserve Met"
    )
    private val testListing2 = Listing(
        listingId = "test02",
        title = "Some other title",
        location = "Christchurch",
        imageUrl = "picture_url",
        buyNowPrice = 1234.56,
        displayPrice = "$1234.56",
        isClassified = false,
        reserveState = "No Reserve"
    )

}