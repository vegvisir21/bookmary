package karpiuk.bookmary.presentation.screens.summary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import karpiuk.bookmary.core_ui.theme.BookmaryTheme

@Composable
fun SummaryScreen(
    modifier: Modifier = Modifier,
) {

    val viewModel: SummaryViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.title.isNotEmpty()) {
        SummaryScreen(
            modifier = modifier,
            uiState = uiState,
        )
    }
}

@Composable
private fun SummaryScreen(
    modifier: Modifier = Modifier,
    uiState: SummaryUiState,
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                text = uiState.title,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Preview
@Composable
private fun SummaryScreenPreview() {
    BookmaryTheme {
        SummaryScreen(
            uiState = SummaryUiState(
                title = "Summary screen"
            ),
        )
    }
}