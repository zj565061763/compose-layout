package com.sd.demo.compose.layout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.unit.dp
import com.sd.demo.compose.layout.theme.AppTheme
import com.sd.lib.compose.layout.FComposeLayout
import com.sd.lib.compose.layout.fComposeLayoutApi
import com.sd.lib.compose.layout.fLayoutNode
import kotlinx.coroutines.delay

class SampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                FComposeLayout {
                    ContentView()
                }
            }
        }
    }
}

@Composable
private fun ContentView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        TargetInfo()
        TargetLayout()
    }
}

@Composable
private fun TargetInfo(
    modifier: Modifier = Modifier,
) {
    val composeLayoutApi = fComposeLayoutApi()
    var targetInfo by remember { mutableStateOf("") }

    LaunchedEffect(composeLayoutApi) {
        while (true) {
            val layoutCoordinates = composeLayoutApi.getLayoutCoordinates("target")
            val position = layoutCoordinates?.positionInWindow() ?: Offset.Zero
            targetInfo = position.toString()
            delay(1_000)
        }
    }

    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = targetInfo)
    }
}

@Composable
private fun TargetLayout(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "target",
            modifier = Modifier.fLayoutNode("target")
        )

        Box(
            modifier = Modifier
                .height(1000.dp)
                .background(Brush.verticalGradient(listOf(Color.Black, Color.White)))
        )
    }
}