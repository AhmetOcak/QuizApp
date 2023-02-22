package com.quizapp.domain.model.quiz

data class Categories(
    val categories: ArrayList<Category>
)

data class Category(
    val id: String,
    val categoryName: String
)