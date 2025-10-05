package gladun.vlad.testme.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import gladun.vlad.testme.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MyTradeMeScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.screen_title_my_trade_me))
                }
            )
        }
    ) { values ->
        Box(
            modifier = Modifier.fillMaxSize().padding(values),
            contentAlignment = Alignment.Center
        ) {
            Text("TODO: My Trade Me")
        }
    }
}