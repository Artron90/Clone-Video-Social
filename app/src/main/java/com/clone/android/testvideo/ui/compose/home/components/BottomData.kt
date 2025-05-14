package com.clone.android.testvideo.ui.compose.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.clone.android.testvideo.compose.theme.WhiteSemi

@Composable
fun BottomData(title: String = "title", subTitle: String = "subtitle") {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .zIndex(98f),
        verticalArrangement = Arrangement.Bottom
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = WhiteSemi)
                .padding(5.dp)
        ) {
            Text(
                title,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                subTitle,
                fontSize = 16.sp,
                color = Color.White
            )
        }

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

            BottomData()

        }

    }

}