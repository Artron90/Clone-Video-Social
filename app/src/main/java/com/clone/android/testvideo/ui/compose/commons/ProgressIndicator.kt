package com.clone.android.testvideo.ui.compose.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.clone.android.testvideo.compose.theme.WhiteTrasparent

@Composable
fun ProgressIndicator() {
    Box(
        Modifier
            .fillMaxSize()
            .background(WhiteTrasparent)
            .zIndex(99f)
            .pointerInput(Unit) {}
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .zIndex(100f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        CircularProgressIndicator(
            modifier = Modifier.width(54.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )

    }
}

@Composable
@Preview(name = "PIXEL_7A", device = Devices.PIXEL_7A, showBackground = true)
@Preview(name = "NEXUS_9", device = Devices.NEXUS_9, showBackground = true)
private fun LivePreview() {

    Scaffold {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            ProgressIndicator()

        }

    }

}