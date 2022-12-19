package com.example.assignment2.models

data class Activity(
    val activity: String,
    val accessibility: Double,
    val type: String,
    val participants: Int,
    val price: Double,
    val link: String,
    val key: String,
    var favourite: Boolean = false,
    var sent: Boolean = false
)

