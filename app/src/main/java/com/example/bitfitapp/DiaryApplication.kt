package com.example.bitfitapp

import android.app.Application

class DiaryApplication: Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}