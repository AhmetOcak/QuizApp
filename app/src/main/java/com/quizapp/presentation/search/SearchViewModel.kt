package com.quizapp.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.quizapp.core.navigation.NavNames
import com.quizapp.core.navigation.Navigator
import com.quizapp.data.datasource.remote.quiz.entity.RecordsDto
import com.quizapp.domain.repository.QuizRepository
import com.quizapp.domain.utils.Messages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

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
        return when (throwable) {
            is IOException -> {
                Messages.INTERNET
            }
            else -> {
                Messages.UNKNOWN
            }
        }
    }

    fun navigateQuizLandingScreen(
        quizId: String,
        quizTitle: String,
        quizDescription: String,
        quizAuthorUserName: String,
        quizCreatedDate: String,
        quizAuthorUserImage: String,
        categoryName: String
    ) {
        Navigator.navigate("${NavNames.quiz_landing_screen}/${quizId}/${quizTitle}/${quizDescription}/${quizAuthorUserName}/${quizCreatedDate}/${quizAuthorUserImage}/${categoryName}") {}
    }

    var temp = -1
    fun produceRandomNumber(): Int {
        var randomNum = Random.nextInt(0..8)

        while (temp == randomNum) {
            randomNum = Random.nextInt(0..8)
        }

        temp = randomNum
        return randomNum
    }
}