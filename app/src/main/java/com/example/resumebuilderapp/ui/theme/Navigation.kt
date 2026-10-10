package com.example.resumebuilderapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.resumebuilderapp.data.ResumeViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.resumebuilderapp.ui.screens.FormWizardScreen

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object TemplateSelector : Screen("template_selector")
    object FormWizard : Screen("form_wizard")
    object SectionManager : Screen("section_manager")
    object PdfPreview : Screen("pdf_preview")
}

@Composable
fun AppNavigation(
    viewModel: ResumeViewModel
) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()

    NavHost(navController = navController, startDestination = Screen.Onboarding.route) {

        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onCreateResumeClick = { navController.navigate(Screen.TemplateSelector.route) },
                onImportDataClick = { navController.navigate(Screen.Home.route) }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                drafts = listOf(
                    DraftModel(uiState.id, uiState.resumeTitle, "Just now")
                ),
                onCreateNewClick = { navController.navigate(Screen.TemplateSelector.route) },
                onEditDraft = { navController.navigate(Screen.FormWizard.route) }
            )
        }

        composable(Screen.TemplateSelector.route) {
            TemplateSelectorScreen(
                onTemplateSelected = { templateId ->
                    viewModel.selectTemplate(templateId)
                    navController.navigate(Screen.FormWizard.route)
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.FormWizard.route) {
            FormWizardScreen(
                resumeData = uiState,
                onPersonalInfoChange = { viewModel.updatePersonalInfo(it) },
                onAddWorkExperience = { viewModel.addWorkExperience(it) },
                onUpdateWorkExperience = { index, exp -> viewModel.updateWorkExperience(index, exp) },
                onRemoveWorkExperience = { index -> viewModel.removeWorkExperience(index) },
                onAddEducation = { viewModel.addEducation(it) },
                onUpdateEducation = { index, edu -> viewModel.updateEducation(index, edu) },
                onRemoveEducation = { index -> viewModel.removeEducation(index) },
                onSkillsChange = { viewModel.updateSkills(it) },
                onFinish = { navController.navigate(Screen.SectionManager.route) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.SectionManager.route) {
            SectionManagerScreen(
                onContinueToPreview = { navController.navigate(Screen.PdfPreview.route) }
            )
        }

        composable(Screen.PdfPreview.route) {
            var showExportSheet by remember { mutableStateOf(false) }

            PdfPreviewScreen(
                onExportClick = { showExportSheet = true },
                onBackClick = { navController.popBackStack() }
            )

            if (showExportSheet) {
                ExportBottomSheet(onDismiss = { showExportSheet = false })
            }
        }
    }
}

