package gladun.vlad.testme.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// region Material Theme
//TODO: Modify default Material colors according to the company's style guide
private val DarkColorScheme = darkColorScheme(
    primary = Tasman500,
    secondary = Feijoa500,
)

private val LightColorScheme = lightColorScheme(
    primary = Tasman500,
    secondary = Feijoa500,
    /* Some default colors to override
    tertiary = Pink40,
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)
// endregion

// region Custom Themes
@Immutable
data class CustomColorScheme(
    val textNormalEmphasis: Color = Color.Unspecified,
    val textLowEmphasis: Color = Color.Unspecified,
    val toolbarActionColor: Color = Color.Unspecified,
    val selectedTabTextColor: Color = Color.Unspecified,
    val unselectedTabTextColor: Color = Color.Unspecified,
)

//TODO: add missing Style Guide colors
private val lightCustomTheme = CustomColorScheme(
    textNormalEmphasis = BluffOyster800,
    textLowEmphasis = BluffOyster600,
    toolbarActionColor = Tasman500,
    selectedTabTextColor = Tasman500,
    unselectedTabTextColor = BluffOyster600
)

private val darkCustomTheme = CustomColorScheme(
    textNormalEmphasis = Color.White,
    textLowEmphasis = Color.LightGray,
    toolbarActionColor = Tasman500,
    selectedTabTextColor = Tasman500,
    unselectedTabTextColor = Color.LightGray
)

val LocalCustomTheme = staticCompositionLocalOf { CustomColorScheme() }
// endregion

// region Theme extensions
val MaterialTheme.spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current
// endregion

@Composable
fun TestMeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val customColorTheme = if (darkTheme) darkCustomTheme else lightCustomTheme

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalCustomTheme provides customColorTheme
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}