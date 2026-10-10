package com.example.resumebuilderapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfPreviewScreen(onExportClick: () -> Unit, onBackClick: () -> Unit) {
    var selectedColor by remember { mutableStateOf(Color.Blue) }
    val colors = listOf(Color.Blue, Color.DarkGray, Color(0xFF006666), Color(0xFF003366))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Live CV Preview") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
                },
                actions = {
                    Button(onClick = onExportClick, modifier = Modifier.padding(end = 8.dp)) {
                        Text("Export PDF")
                    }
                }
            )
        },
        bottomBar = {
            Surface(tonalElevation = 8.dp, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Theme Palette", style = MaterialTheme.typography.labelMedium)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        colors.forEach { color ->
                            IconButton(
                                onClick = { selectedColor = color },
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(color, shape = CircleShape)
                            ) {}
                        }
                    }
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {

            Card(
                modifier = Modifier
                    .fillMaxHeight(0.85f)
                    .fillMaxWidth(0.85f),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Rendered PDF Canvas Page", color = Color.Gray)
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PdfPreviewScreenPreview() {
    ResumeBuilderAppTheme {
        PdfPreviewScreen(onExportClick = {}, onBackClick = {})
    }
}