package gladun.vlad.testme.presentation.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import gladun.vlad.testme.domain.model.UiText

fun Context.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

@Composable
fun PaddingValues.copyPaddings(
    start: Dp = this.calculateStartPadding(LocalLayoutDirection.current),
    top: Dp = this.calculateTopPadding(),
    end: Dp = this.calculateEndPadding(LocalLayoutDirection.current),
    bottom: Dp = this.calculateBottomPadding()
): PaddingValues {
    return PaddingValues(start = start, top = top, end = end, bottom = bottom)
}

fun Int.toUiText() = UiText.StringResource(this)