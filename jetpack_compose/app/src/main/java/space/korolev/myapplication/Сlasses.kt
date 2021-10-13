package space.korolev.myapplication

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Response(
    val results:List<CharacterRickAndMorty>
)

@JsonClass(generateAdapter = true)
data class RickMortyLocation(
    val id: Long,
    val name: String
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