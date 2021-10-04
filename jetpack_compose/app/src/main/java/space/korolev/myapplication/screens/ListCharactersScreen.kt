package space.korolev.myapplication.screens

import android.widget.ListView
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import kotlinx.coroutines.flow.Flow
import space.korolev.myapplication.api.Character
import space.korolev.myapplication.list.Comment
import space.korolev.myapplication.list.GlideImageWithPreview
import space.korolev.myapplication.list.ListPagingSource
import space.korolev.myapplication.list.ListViewModel


val pageData by lazy {ListPagingSource.pager(viewModel = ListViewModel()).flow} // видимо здесь не работает

@Composable
fun ListCharactersScreen(navController: NavController) {

    /*val viewModel by viewModels<ListViewModel> {
        object :ViewModelProvider.Factory {
            override fun <T :ViewModel?> create(modelClass: Class<T>):T=ListViewModel() as T
        }
    }*/

    //val context = LocalContext.current
    Scaffold {
        /*Text("List Characters Screen",
            modifier = Modifier
                .padding(24.dp)
                .clickable(onClick = {
                    Toast
                        .makeText(context, "Pressed TEXT", Toast.LENGTH_SHORT)
                        .show()
                    navController.navigate("hello")
                }
                )
        )*/
        ListView()

    }
}

@Composable
fun ListView() {
    val items: LazyPagingItems<Comment> = pageData.collectAsLazyPagingItems()

    LazyColumn {
        items(items) {
            it?.let { CommentView(comment = it) } ?: Text(text = "Ooops")
        }

        items.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = items.loadState.refresh as LoadState.Error
                    item {
                        Column(modifier = Modifier.fillParentMaxSize()) {
                            e.error.localizedMessage?.let { Text(text = it) }
                            Button(onClick = { retry() }) {
                                Text(text = "Retry")
                            }
                        }

                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = items.loadState.append as LoadState.Error
                    item {
                        Column(
                            modifier = Modifier.fillParentMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            e.error.localizedMessage?.let { Text(text = it) }
                            Button(onClick = { retry() }) {
                                Text(text = "Retry")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CommentView(comment: Comment) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(6.dp)
    )
    {
        GlideImageWithPreview(data = comment.image, Modifier.size(60.dp))
        Column(modifier = Modifier.padding(start = 6.dp)) {
            Text(
                text = comment.name,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
            )
            Text(
                text = comment.text,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp,
            )
        }
    }
}