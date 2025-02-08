package com.swanand.fetchviewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val repository: FetchRepository) : ViewModel() {

    private val _items = MutableStateFlow<Map<Int, List<ListItem>>>(emptyMap())
    val items: StateFlow<Map<Int, List<ListItem>>> = _items

    private val _progressStateFlow = MutableStateFlow(FetchState.Loading)
    val progressStateFlow: StateFlow<FetchState> = _progressStateFlow

    init {
        fetchData()
    }
    enum class FetchState {
        Loading, Success, Error
    }

    private fun fetchData() {
        viewModelScope.launch {
            _progressStateFlow.value = FetchState.Loading
            try {
                repository.getProcessedItems().collect {
                    _items.value = it
                }
                _progressStateFlow.value = FetchState.Success
            } catch (e: Exception) {
                _progressStateFlow.value = FetchState.Error
            }
        }
    }

    fun retryFetch() {
        fetchData()
    }
}
