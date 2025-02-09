package com.org.childmonitorparent.parent.models

data class LocationData(
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
    val childId: String
)