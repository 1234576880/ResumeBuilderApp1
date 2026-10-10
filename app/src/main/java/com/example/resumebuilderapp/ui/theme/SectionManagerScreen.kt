package com.example.resumebuilderapp.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class SectionItem(val name: String, var isVisible: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectionManagerScreen(onContinueToPreview: () -> Unit) {
    var sections by remember {
        mutableStateOf(
            listOf(
                SectionItem("Personal Info", true),
                SectionItem("Work Experience", true),
                SectionItem("Education", true),
                SectionItem("Skills", true),
                SectionItem("Projects", false)
            )
        )
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Arrange Resume Structure") }) },
        bottomBar = {
            Surface(tonalElevation = 4.dp, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onContinueToPreview,
                    modifier = Modifier.padding(16.dp).fillMaxWidth()
                ) { Text("Preview PDF") }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                itemsIndexed(sections) { index, item ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(12.dp).fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.DragHandle, contentDescription = "Reorder")
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(item.name, modifier = Modifier.weight(1f))
                            IconButton(onClick = {
                                sections = sections.toMutableList().also {
                                    it[index] = it[index].copy(isVisible = !it[index].isVisible)
                                }
                            }) {
                                Icon(
                                    imageVector = if (item.isVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Toggle Visibility"
                                )
                            }
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }

            OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.Add, contentDescription = null)
                Text("Add Custom Section")
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun SectionScreenPreview() {
    ResumeBuilderAppTheme {
        SectionManagerScreen(onContinueToPreview = {})
    }
}