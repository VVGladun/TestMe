package gladun.vlad.testme.presentation.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import gladun.vlad.testme.presentation.ui.theme.LocalCustomTheme

@Composable
fun TopBarAction(
    @DrawableRes iconResId: Int,
    @StringRes description: Int,
    tintColor: Color = LocalCustomTheme.current.toolbarActionColor,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(iconResId),
            contentDescription = stringResource(description),
            tint = tintColor
        )
    }
}