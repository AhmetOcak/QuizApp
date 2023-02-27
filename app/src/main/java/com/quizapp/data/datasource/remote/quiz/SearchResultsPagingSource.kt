package com.quizapp.data.datasource.remote.quiz

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.quizapp.data.datasource.remote.quiz.entity.RecordsDto
import java.io.IOException
import javax.inject.Inject

class SearchResultsPagingSource @Inject constructor(
    private val remoteDataSource: QuizRemoteDataSource,
    private val searchKeyword: String
) : PagingSource<Int, RecordsDto>() {

    override fun getRefreshKey(state: PagingState<Int, RecordsDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecordsDto> {
        return try {
            val page = params.key ?: 1
            val response = remoteDataSource.searchQuiz(searchKeyword, page)

            LoadResult.Page(
                data = response.results.records,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.results.records.isEmpty()) null else page.plus(1),
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}