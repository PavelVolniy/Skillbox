package com.example.recyclerviewapp.entity

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.recyclerviewapp.data.PhotosDataSource
import com.example.recyclerviewapp.data.nasarapi.RetrofitApi
import javax.inject.Inject

class PhotoPagingSource @Inject constructor(
    private val retrofitApi: RetrofitApi
) : PagingSource<Int, Photo>() {
    private val repo: PhotosDataSource = PhotosDataSource(retrofitApi)
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repo.getPhotos(page)
        }.fold(onSuccess = {
            LoadResult.Page(
                data = it,
                prevKey = null,
                nextKey = if (it.isEmpty()) null else page + 1
            )
        },
            onFailure = { LoadResult.Error(it) }
        )
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}