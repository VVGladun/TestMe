package gladun.vlad.testme.domain.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable

/**
 * Base VM Loading / Content / Error State.
 *
 * THIS IS JUST TO SHOW CASE THE APPROACH
 * Having something like that, I could've also created a base VM classes with error handling
 * and enhance it further by adding some KnownError class for API and other types of errors
 * and have a base error handling mapping known errors to error data payloads
 * (I won't overengineer it due to the time constraints though)
 */
@Stable
data class ScreenLceState<out T> (
    val loading: LoadingState = LoadingState.None,
    val content: T? = null,
    val error: ErrorData? = null
)

sealed class LoadingState {
    object None : LoadingState()
    // Different Loading and Refreshing states in case we want treat initial/full reload and pull to refresh refresh UI states differently
    object Loading : LoadingState()
    object Refreshing : LoadingState()
}

data class ErrorData(
    val title: UiText?,
    val message: UiText?,
    val primaryActionLabel: UiText,
    val secondaryActionLabel: UiText?,
)

// Just to avoid leaking context in the view model while still be able to use string resources in VM
sealed class UiText {
    data class SimpleText(val text: String) : UiText()
    data class StringResource(@StringRes val resId: Int) : UiText()
}
