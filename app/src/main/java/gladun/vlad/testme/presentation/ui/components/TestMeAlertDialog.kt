package gladun.vlad.testme.presentation.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import gladun.vlad.testme.domain.model.UiText
import gladun.vlad.testme.presentation.ui.theme.LocalCustomTheme
import gladun.vlad.testme.presentation.ui.toText

@Composable
fun TestMeAlertDialog(
    dialogTitle: UiText?,
    dialogMessage: UiText?,
    primaryLabel: UiText,
    secondaryLabel: UiText?,
    onPrimaryAction: () -> Unit,
    onSecondaryAction: () -> Unit,
    onDismiss: () -> Unit,
    icon: ImageVector? = null,
    iconDescription: String? = null,
) {
    AlertDialog(
        icon = {
            icon?.let {
                Icon(icon, contentDescription = iconDescription ?: "error dialog icon")
            }
        },
        title = {
            dialogTitle?.let {
                Text(
                    text = dialogTitle.toText(),
                    color = LocalCustomTheme.current.textNormalEmphasis
                )
            }
        },
        text = {
            dialogMessage?.let {
                Text(
                    text = dialogMessage.toText(),
                    color = LocalCustomTheme.current.textNormalEmphasis
                )
            }
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onPrimaryAction()
                }
            ) {
                Text(
                    text = primaryLabel.toText(),
                    color = LocalCustomTheme.current.actionColor
                )
            }
        },
        dismissButton = {
            secondaryLabel?.let {
                TextButton(
                    onClick = {
                        onSecondaryAction.invoke()
                    }
                ) {
                    Text(
                        text = secondaryLabel.toText(),
                        color = LocalCustomTheme.current.actionColor
                    )
                }
            }
        }
    )
}