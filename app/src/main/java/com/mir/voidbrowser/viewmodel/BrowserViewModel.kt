package com.mir.voidbrowser.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BrowserViewModel: ViewModel() {
    private var _url = MutableStateFlow("https://www.google.com")
    val url = _url.asStateFlow()

    fun updateUrl(newUrl: String) {
        _url.value = newUrl
    }
}