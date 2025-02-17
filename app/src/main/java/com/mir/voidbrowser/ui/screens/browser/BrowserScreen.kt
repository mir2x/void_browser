package com.mir.voidbrowser.ui.screens.browser

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.viewinterop.AndroidView
import com.mir.voidbrowser.ui.components.NavigationControls
import com.mir.voidbrowser.viewmodel.BrowserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun BrowserScreen(viewModel: BrowserViewModel) {
    val context = LocalContext.current
    val webView = remember { WebView(context) }
    val coroutineScope = rememberCoroutineScope()

    var url by remember { mutableStateOf("https://www.google.com") }
    var isSearchActive by remember { mutableStateOf(false) }
    var progress by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        webView.apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                cacheMode = WebSettings.LOAD_DEFAULT
            }
            webViewClient = object : WebViewClient() {}
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    progress = newProgress
                }
            }
            loadUrl(url)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Void Browser") })

        SearchBar(
            query = url,
            onQueryChange = { url = it },
            onSearch = { newUrl ->
                url = newUrl
                coroutineScope.launch {
                    webView.loadUrl(url)
                }
            },
            active = isSearchActive,
            onActiveChange = { isSearchActive = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = url,
                onValueChange = { url = it },
                placeholder = { Text("Enter URL") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(onGo = {
                    // Perform search when Go is pressed
                    coroutineScope.launch {
                        webView.loadUrl(url)
                    }
                }),
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (progress in 1..99) {
            LinearProgressIndicator(
                progress = { progress / 100f },
                modifier = Modifier.fillMaxWidth(),
            )
        }
        AndroidView(
            modifier = Modifier.weight(1f),
            factory = { webView }
        )

        NavigationControls(
            onBack = { if (webView.canGoBack()) webView.goBack() },
            onForward = { if (webView.canGoForward()) webView.goForward() },
            onRefresh = { webView.reload() }
        )
    }
}
