package gladun.vlad.testme.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gladun.vlad.testme.R
import gladun.vlad.testme.domain.model.ErrorData
import gladun.vlad.testme.domain.model.Listing
import gladun.vlad.testme.domain.model.LoadingState
import gladun.vlad.testme.domain.model.ScreenLceState
import gladun.vlad.testme.domain.repository.LatestListingRepository
import gladun.vlad.testme.presentation.ui.toUiText
import gladun.vlad.testme.utils.Constants
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LatestListingsViewModel @Inject constructor(
    private val latestListingRepository: LatestListingRepository
) : ViewModel() {

    private val initialState = ScreenLceState<List<Listing>>(
        loading = LoadingState.Loading,
        content = emptyList(),
        error = null
    )

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<ScreenLceState<List<Listing>>> = _uiState.onStart {
        // trigger initial refresh
        refreshData(isRefresh = false)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = Constants.UI_STATE_SUBSCRIPTION_TIMEOUT_MS),
        initialValue = initialState
    )

    private var refreshJob: Job? = null

    fun onPullToRefresh() {
        refreshData(isRefresh = true)
    }

    fun onErrorDialogDismiss() {
        _uiState.update {
            it.copy(
                loading = LoadingState.None,
                error = null
            )
        }
    }

    fun onErrorDialogRetry() {
        refreshData(isRefresh = _uiState.value.content?.isNotEmpty() == true)
    }

    private fun refreshData(isRefresh: Boolean) {
        if (refreshJob?.isActive == true) {
            return
        }

        refreshJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    loading = if (isRefresh) LoadingState.Refreshing else LoadingState.Loading,
                    error = null
                )
            }
            try {
                latestListingRepository.getLatestListings().catch { e ->
                    throw e
                }.collect { listing ->
                    _uiState.update {
                        it.copy(
                            content = listing,
                            loading = LoadingState.None
                        )
                    }
                }
            } catch (e: Exception) {
                if (e !is CancellationException) {
                    _uiState.update {
                        it.copy(
                            error = ErrorData(
                                title = R.string.error_generic_title.toUiText(),
                                message = R.string.error_generic_message.toUiText(),
                                primaryActionLabel = R.string.error_retry.toUiText(),
                                secondaryActionLabel = R.string.error_dismiss.toUiText()
                            ),
                            loading = LoadingState.None
                        )
                    }
                }
            }
        }
    }
}