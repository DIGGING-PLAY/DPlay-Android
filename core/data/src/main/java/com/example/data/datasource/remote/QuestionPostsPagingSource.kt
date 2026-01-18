package com.example.data.datasource.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.model.response.QuestionPostItemResponse
import com.example.data.service.PostService

class QuestionPostsPagingSource(
    private val postService: PostService,
    private val questionId: Long,
    private val onTotalCountFetched: (Int) -> Unit,
) : PagingSource<String, QuestionPostItemResponse>() {
    override fun getRefreshKey(state: PagingState<String, QuestionPostItemResponse>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, QuestionPostItemResponse> =
        try {
            val currentCursor = params.key

            val response =
                postService.getPostsByQuestionId(
                    questionId = questionId,
                    cursor = currentCursor,
                    limit = params.loadSize,
                )

            val data = response.data ?: throw Exception("data is null")
            if (params.key == null) {
                onTotalCountFetched(data.totalCount)
            }
            val posts = data.items
            val nextCursor = data.nextCursor

            LoadResult.Page(
                data = posts,
                prevKey = null,
                nextKey = if (posts.isEmpty()) null else nextCursor,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
}
