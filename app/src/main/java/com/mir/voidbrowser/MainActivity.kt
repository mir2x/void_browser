package com.mir.voidbrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mir.voidbrowser.ui.screens.browser.BrowserScreen
import com.mir.voidbrowser.viewmodel.BrowserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val browserViewModel = BrowserViewModel()
            BrowserScreen(viewModel = browserViewModel)
        }
    }
}