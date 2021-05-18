package com.codemitry.wearer

import android.app.Application
import android.app.Instrumentation
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.codemitry.wearer.app.TestApp

class TestAppRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application = Instrumentation.newApplication(TestApp::class.java, context)
}