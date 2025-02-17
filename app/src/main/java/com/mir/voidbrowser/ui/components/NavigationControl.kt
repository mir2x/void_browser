package com.mir.voidbrowser.ui.components
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NavigationControls(onBack: () -> Unit, onForward: () -> Unit, onRefresh: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = onBack) { Text("Back") }
        Button(onClick = onForward) { Text("Forward") }
        Button(onClick = onRefresh) { Text("Refresh") }
    }
}
