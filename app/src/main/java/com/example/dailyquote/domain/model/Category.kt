package com.example.dailyquote.domain.model

import com.example.dailyquote.R

data class Category(
    val apiName: String,
    val displayName: String,
    val iconResId: Int
)

val categories = listOf(
    Category("wisdom", "Wisdom", R.drawable.ic_wisdom),
    Category("philosophy", "Philosophy", R.drawable.ic_philosophy),
    Category("life", "Life", R.drawable.ic_life),
    Category("truth", "Truth", R.drawable.ic_truth),
    Category("inspirational", "Inspirational", R.drawable.ic_inspirational),
    Category("relationships", "Relationships", R.drawable.ic_relationships),
    Category("love", "Love", R.drawable.ic_love),
    Category("humor", "Humor", R.drawable.ic_humor),
    Category("success", "Success", R.drawable.ic_success),
    Category("courage", "Courage", R.drawable.ic_courage),
    Category("happiness", "Happiness", R.drawable.ic_happiness),
    Category("art", "Art", R.drawable.ic_art),
    Category("writing", "Writing", R.drawable.ic_writing),
    Category("fear", "Fear", R.drawable.ic_fear),
    Category("nature", "Nature", R.drawable.ic_nature),
    Category("time", "Time", R.drawable.ic_time),
    Category("freedom", "Freedom", R.drawable.ic_freedom),
    Category("death", "Death", R.drawable.ic_death),
    Category("leadership", "Leadership", R.drawable.ic_leadership)
)
