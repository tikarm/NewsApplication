package com.tigran.applications.newsapplication

import android.app.Application
import com.tigran.applications.newsapplication.data.local.db.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
}