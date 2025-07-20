package karpiuk.bookmary.presentation.screens.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SummaryViewModel @Inject constructor(
    //TODO future use-cases
) : ViewModel() {

    private val _uiState = MutableStateFlow(SummaryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                SummaryUiState(
                    title = "Summary screen"
                )
            }
        }
    }

}