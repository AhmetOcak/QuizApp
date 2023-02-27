package com.quizapp.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.quizapp.data.datasource.remote.quiz.entity.RecordsDto
import com.quizapp.domain.repository.QuizRepository
import com.quizapp.domain.utils.Messages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    var searchKeyword by mutableStateOf("")
        private set

    fun updateSearchField(newValue: String) { searchKeyword = newValue }

    fun isSearchKeywordBlank(): Boolean = searchKeyword.isBlank()

    fun getSearchResults(): Flow<PagingData<RecordsDto>> =
        quizRepository.searchQuiz(searchKeyword = searchKeyword).cachedIn(viewModelScope)

    fun setErrorMessage(throwable: Throwable): String {
        return when(throwable) {
            is IOException -> {
                Messages.INTERNET
            }
            else -> {
                Messages.UNKNOWN
            }
        }
    }
}