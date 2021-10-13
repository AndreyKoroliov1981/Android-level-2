package space.korolev.myapplication.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import space.korolev.myapplication.RickMortyLocation

class ListLocationPagingSource (
    private val viewModel: ListLocationDataSource
) : PagingSource<Int, RickMortyLocation>() {
    override fun getRefreshKey(state: PagingState<Int, RickMortyLocation>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RickMortyLocation> =
        kotlin.runCatching {
            viewModel.load(params.key?:0) }.fold(
            onSuccess = { list ->
                LoadResult.Page(
                    data=list,
                    prevKey = params.key?.let { it-1 },
                    nextKey = (params.key?:0)+1
                )
            },
            onFailure = {throwable-> LoadResult.Error(throwable)}
        )
}