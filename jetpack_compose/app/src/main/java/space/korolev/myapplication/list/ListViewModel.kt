package space.korolev.myapplication.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class Comment(val image: String?, val name: String, val text: String)

class ListViewModel: ViewModel() {
    suspend fun load(page:Int):List<Comment> {
        delay(500L)
        return (0..10).map {
            Comment(
                image="https://picsum.photos/300/300",
                name="${page*10+it} Hello",
                text= "test text"
            )
        }
    }
}