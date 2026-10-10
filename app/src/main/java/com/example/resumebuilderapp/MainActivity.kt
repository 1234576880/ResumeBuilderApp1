package com.example.resumebuilderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resumebuilderapp.data.ResumeViewModel
import com.example.resumebuilderapp.ui.theme.AppNavigation
import com.example.resumebuilderapp.ui.theme.OnboardingScreen
import com.example.resumebuilderapp.ui.theme.ResumeBuilderAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val resumeViewModel: ResumeViewModel = viewModel()
            ResumeBuilderAppTheme {
                     AppNavigation(viewModel = resumeViewModel)

                }

        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ResumeBuilderAppTheme {

    }
}
