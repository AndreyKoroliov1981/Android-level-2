package space.korolev.myapplication.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import space.korolev.myapplication.RickMortyLocation
import space.korolev.myapplication.api.RetrofitApi

class ListLocationDataSource (
) :  ViewModel(){

    suspend fun load(page:Int):List<RickMortyLocation>{
        var list: List<RickMortyLocation> = emptyList()
        val localScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        val job = localScope.launch {
            try {
                list = RetrofitApi.myApi.loadListLocations(page=page).results

            } catch (t: Throwable) {
                list=listOf(
                    RickMortyLocation(
                        -1,
                        "Failed to get a response from the server",
                        "Check network connection"
                    )
                )
            }
        }
        job.join()
        delay(50)
        localScope.cancel()
        return list
    }

}