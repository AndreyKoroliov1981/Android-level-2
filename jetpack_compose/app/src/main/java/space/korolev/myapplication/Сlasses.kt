package space.korolev.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.squareup.moshi.JsonClass
import space.korolev.myapplication.screens.ListCharactersScreen
import space.korolev.myapplication.screens.ListLocationsScreen

@JsonClass(generateAdapter = true)
class Response(
    val results:List<CharacterRickAndMorty>
)

@JsonClass(generateAdapter = true)
data class RickMortyLocation(
    val id: Long,
    val name: String,
    val type: String
)

@JsonClass(generateAdapter = true)
class Locations(
    val results: List<RickMortyLocation>
)

@JsonClass(generateAdapter = true)
data class CharacterRickAndMorty(
    val id:Long,
    val name:String,
    val status: String,
    val species:String,
    val location: LastLocation,
    val image: String,
    val episode : List<String>
)

@JsonClass(generateAdapter = true)
data class LastLocation(
    val name: String,
    val  url: String
)

typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var title: String, var screen: ComposableFun) {
    object tabCharacters : TabItem( "Characters", { ListCharactersScreen() })
    object tabLocations : TabItem( "Locations", { ListLocationsScreen() })
}