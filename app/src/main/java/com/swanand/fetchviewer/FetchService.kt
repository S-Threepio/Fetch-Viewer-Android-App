package com.swanand.fetchviewer

import retrofit2.http.GET

interface FetchService {
    @GET("hiring.json")
    suspend fun fetchItems(): List<ListItem>
}
