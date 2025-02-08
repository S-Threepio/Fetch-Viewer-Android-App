package com.swanand.fetchviewer.repository

import com.swanand.fetchviewer.network.FetchService
import com.swanand.fetchviewer.ListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchRepository @Inject constructor(private val api: FetchService) {
    fun getProcessedItems(): Flow<Map<Int, List<ListItem>>> = flow {
        val response = api.fetchItems()
        val filteredSortedData = response
            .filter { it.name?.isNotBlank() == true } // Remove null/empty names
            .sortedWith(compareBy({ it.listId }, { it.name })) // Sort by listId, then name
            .groupBy { it.listId } // Group by listId
        emit(filteredSortedData)
    }
}
