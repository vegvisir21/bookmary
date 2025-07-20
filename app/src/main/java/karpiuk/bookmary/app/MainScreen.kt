package karpiuk.bookmary.app

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController

@Composable
internal fun MainScreen() {

    val navController = rememberNavController()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            contentWindowInsets = WindowInsets(0.dp)
        ) {
            AppNavHost(
                modifier = Modifier.padding(it),
                navController = navController,
            )
        }
    }
}