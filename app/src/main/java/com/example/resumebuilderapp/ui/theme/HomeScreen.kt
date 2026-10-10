package com.example.resumebuilderapp.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class DraftModel(val id: String, val title: String, val lastUpdated: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    drafts: List<DraftModel>,
    onCreateNewClick: () -> Unit,
    onEditDraft: (String) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Professional Resume Builder", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Language, contentDescription = "Language") }
                    IconButton(onClick = {}) { Icon(Icons.Default.Settings, contentDescription = "Settings") }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateNewClick) {
                Icon(Icons.Default.Add, contentDescription = "Create New")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Create New Resume", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("Select a template and build your CV", fontSize = 12.sp)
                    }
                    Button(onClick = onCreateNewClick) {
                        Text("Start")
                    }
                }
            }


            TabRow(selectedTabIndex = selectedTab, modifier = Modifier.padding(bottom = 12.dp)) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                    Text("My Resumes (${drafts.size})", modifier = Modifier.padding(12.dp))
                }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                    Text("Saved PDFs", modifier = Modifier.padding(12.dp))
                }
            }


            if (selectedTab == 0) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(drafts) { draft ->
                        ResumeItemCard(draft = draft, onEdit = { onEditDraft(draft.id) })
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No saved PDFs found.")
                }
            }
        }
    }
}

@Composable
fun ResumeItemCard(draft: DraftModel, onEdit: () -> Unit) {
    var menuExpanded by remember { mutableStateOf(false) }

    OutlinedCard(
        onClick = onEdit,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Article,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(draft.title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text(draft.lastUpdated, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Box {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                }
                DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                    DropdownMenuItem(text = { Text("Edit") }, onClick = { menuExpanded = false; onEdit() })
                    DropdownMenuItem(text = { Text("Duplicate") }, onClick = { menuExpanded = false })
                    DropdownMenuItem(text = { Text("Rename") }, onClick = { menuExpanded = false })
                    DropdownMenuItem(text = { Text("Delete", color = MaterialTheme.colorScheme.error) }, onClick = { menuExpanded = false })
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ResumeBuilderAppTheme {
       HomeScreen(drafts = emptyList(), onCreateNewClick = {}, onEditDraft = {})
    }
}