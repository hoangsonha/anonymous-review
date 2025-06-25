package com.example.anonymousreview.model

data class Review(
    val tag: String,
    val title: String,
    val content: String,
    val images: List<Int>,
    val likes: Int,
    val comments: Int,
    val views: Int
)

val dummyReviews = listOf(
    Review("công nghệ", "Great new smartphone", "The camera quality is amazing...", listOf(), 1, 2, 42),
    Review("công nghệ", "RK75 xài ngon quá nè", "Bữa tui được bạn giới thiệu cho...", listOf(), 10, 4, 50)
)