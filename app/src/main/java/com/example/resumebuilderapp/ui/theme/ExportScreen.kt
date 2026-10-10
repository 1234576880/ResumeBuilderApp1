package com.example.resumebuilderapp.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportBottomSheet(onDismiss: () -> Unit) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Your Resume is Ready!", fontSize = 20.sp, fontWeight = FontWeight.Bold)


            OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Software_Engineer_Resume.pdf", fontWeight = FontWeight.SemiBold)
                        Text("Size: 245 KB • 1 Page", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(48.dp)) {
                Icon(Icons.Default.Download, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Download PDF to Device")
            }

            OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth().height(48.dp)) {
                Icon(Icons.Default.Share, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Share via...")
            }

            TextButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.Print, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Print Document")
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ExportScreenPreview() {
    ResumeBuilderAppTheme {
ExportBottomSheet (onDismiss = {})
    }
}