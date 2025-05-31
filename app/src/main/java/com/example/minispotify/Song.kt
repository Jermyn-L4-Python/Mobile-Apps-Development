package com.example.minispotify

data class Song(
    val title: String,
    val fileResId: Int,
    var isFavorite: Boolean = false
)