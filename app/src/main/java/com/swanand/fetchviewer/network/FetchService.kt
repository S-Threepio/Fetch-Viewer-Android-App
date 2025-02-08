package com.swanand.fetchviewer.network

import com.swanand.fetchviewer.ListItem
import retrofit2.http.GET

interface FetchService {
    @GET("hiring.json")
    suspend fun fetchItems(): List<ListItem>
}
