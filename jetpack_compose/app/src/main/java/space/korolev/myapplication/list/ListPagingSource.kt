package space.korolev.myapplication.list

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState

class ListPagingSource(
    private val viewModel: ListViewModel
) : PagingSource<Int, Comment>() {
    override fun getRefreshKey(state: PagingState<Int, Comment>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comment> =
        kotlin.runCatching {
            viewModel.load(params.key?:0) }.fold(
            onSuccess = { list ->
                LoadResult.Page(
                    data=list,
                    prevKey = params.key?.let { it-1 },
                    nextKey = (params.key?:0)+1
                )
            },
            onFailure = {throwable->LoadResult.Error(throwable)}
            )
    companion object{
        fun pager(viewModel: ListViewModel)=Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {ListPagingSource(viewModel)},
        )
        }
}