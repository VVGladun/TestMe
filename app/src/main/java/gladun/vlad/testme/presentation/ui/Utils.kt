package gladun.vlad.testme.presentation.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import gladun.vlad.testme.domain.model.UiText
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

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

@Composable
fun UiText.toText(): String {
    return when (this) {
        is UiText.SimpleText -> this.text
        is UiText.StringResource -> stringResource(this.resId)
    }
}

fun Double.toDollarsAndCents(): String {
    val format = NumberFormat.getCurrencyInstance(Locale.ENGLISH)
    format.maximumFractionDigits = 2
    format.currency = Currency.getInstance("USD")
    return format.format(this)
}