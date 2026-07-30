package com.example.dailyquote.domain.model

import com.example.dailyquote.R

data class Category(
    val name: String,
    val iconResId: Int
)

val categories = listOf(
    Category("wisdom", R.drawable.ic_wisdom),
    Category("philosophy", R.drawable.ic_philosophy),
    Category("life", R.drawable.ic_life),
    Category("truth", R.drawable.ic_truth),
    Category("inspirational", R.drawable.ic_inspirational),
    Category("relationships", R.drawable.ic_relationships),
    Category("love", R.drawable.ic_love),
    Category("humor", R.drawable.ic_humor),
    Category("success", R.drawable.ic_success),
    Category("courage", R.drawable.ic_courage),
    Category("happiness", R.drawable.ic_happiness),
    Category("art", R.drawable.ic_art),
    Category("writing", R.drawable.ic_writing),
    Category("fear", R.drawable.ic_fear),
    Category("nature", R.drawable.ic_nature),
    Category("time", R.drawable.ic_time),
    Category("freedom", R.drawable.ic_freedom),
    Category("death", R.drawable.ic_death),
    Category("leadership", R.drawable.ic_leadership)
)
