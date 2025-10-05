package gladun.vlad.testme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import gladun.vlad.testme.presentation.navigation.MainNavGraph
import gladun.vlad.testme.presentation.ui.theme.TestMeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestMeTheme {
                MainNavGraph(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}