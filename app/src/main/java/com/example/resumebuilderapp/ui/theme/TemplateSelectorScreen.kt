package com.example.resumebuilderapp.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class TemplateItem(val id: String, val name: String, val badge: String?)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateSelectorScreen(
    onTemplateSelected: (String) -> Unit,
    onBackClick: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedTemplateId by remember { mutableStateOf("1") }

    val categories = listOf("All", "ATS Friendly", "Modern", "Professional", "Minimal")
    val templates = listOf(
        TemplateItem("1", "Modern Clean", "ATS Approved"),
        TemplateItem("2", "Corporate Elite", "Popular"),
        TemplateItem("3", "Minimalist Tech", "ATS Approved"),
        TemplateItem("4", "Creative Executive", null)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Choose Template") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Surface(tonalElevation = 8.dp, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { onTemplateSelected(selectedTemplateId) },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Use This Template", fontSize = 16.sp)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.take(3).forEach { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat) }
                    )
                }
            }


            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                items(templates) { item ->
                    val isSelected = item.id == selectedTemplateId
                    Card(
                        onClick = { selectedTemplateId = item.id },
                        border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                    ) {
                        Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                item.badge?.let {
                                    Badge(containerColor = MaterialTheme.colorScheme.tertiary) {
                                        Text(it, modifier = Modifier.padding(2.dp))
                                    }
                                }
                                Text(item.name, fontWeight = FontWeight.Bold)
                            }
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedTemplateId = item.id },
                                modifier = Modifier.align(Alignment.TopEnd)
                            )
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun TemplateScreenPreview() {
    ResumeBuilderAppTheme {
        TemplateSelectorScreen(onTemplateSelected = {}, onBackClick = {})
    }
}