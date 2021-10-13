package space.korolev.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.paging.*
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import kotlinx.coroutines.flow.Flow
import space.korolev.myapplication.RickMortyLocation
import space.korolev.myapplication.list.ListLocationDataSource
import space.korolev.myapplication.list.ListLocationPagingSource

class MainViewModelLocation(
    dataRepository: ListLocationDataSource
) : ViewModel() {

    val pageData: Flow<PagingData<RickMortyLocation>> = Pager(PagingConfig(pageSize = 10)) {
        ListLocationPagingSource(dataRepository)
    }.flow
}

@Composable
fun ListLocationsScreen() {

    Scaffold {
        ListViewLocations()
    }
}

@Composable
fun ListViewLocations() {
    val items: LazyPagingItems<RickMortyLocation> = MainViewModelLocation(ListLocationDataSource()).pageData.collectAsLazyPagingItems()


    LazyColumn {
        items(items) {
            it?.let { CommentView2(comment = it) } ?: Text(text = "Ooops")
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
fun CommentView2(comment: RickMortyLocation) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(6.dp)
    )
    {

        Column(modifier = Modifier.padding(start = 6.dp)) {
            Text(
                text = comment.name,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
            )
            Text(
                text = "id ${comment.id}",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 20.sp,
            )
            Text(
                text = "type ${comment.type}",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 20.sp,
            )
            Divider(color = Color.Black)
        }
    }
}
