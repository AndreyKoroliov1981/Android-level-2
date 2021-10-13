package space.korolev.myapplication.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import space.korolev.myapplication.CharacterRickAndMorty
import space.korolev.myapplication.LastLocation
import space.korolev.myapplication.api.RetrofitApi


class CharacterDataSource(
) :  ViewModel(){

     suspend fun load(page:Int):List<CharacterRickAndMorty>{
        var list: List<CharacterRickAndMorty> = emptyList()
        val localScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        val job = localScope.launch {
            try {
                list = RetrofitApi.myApi.loadListSuspend(page=page).results

            } catch (t: Throwable) {
                    list=listOf(
                        CharacterRickAndMorty(
                            -1,
                            "Something went wrong",
                            "Failed to get a response from the server",
                            "Check network connection",
                            LastLocation("", ""),
                            "",
                            emptyList()
                        )
                    )
                //)
            }
        }
        job.join()
        delay(50)
        localScope.cancel()
         return list
    }

}