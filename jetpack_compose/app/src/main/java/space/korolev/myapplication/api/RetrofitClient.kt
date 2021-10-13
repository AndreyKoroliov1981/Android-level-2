package space.korolev.myapplication.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import space.korolev.myapplication.Locations
import space.korolev.myapplication.Response

interface Api {
    @GET("/api/character")
    fun loadList(@Query("page") page:Int): Call<Response>

    @GET("/api/character")
    suspend fun loadListSuspend(@Query("page") page: Int): Response

    @GET("/api/location")
    suspend fun loadListLocations(@Query("page") page: Int): Locations

    companion object{
        const val pageSize = 20
    }
}



