package com.example.bitfitapp

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

data class DisplayDiary (
    val title: String?,
    val date: String?,
    val entry: String?,
    val mood: String?
    ) : java.io.Serializable