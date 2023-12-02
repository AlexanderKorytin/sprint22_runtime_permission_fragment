package com.example.sprint22_runtime_permission_fragment

import android.app.Application
import com.markodevcic.peko.PermissionRequester

class App: Application() {
    override fun onCreate() {
        super.onCreate()
       // PermissionRequester.initialize(applicationContext)
    }
}