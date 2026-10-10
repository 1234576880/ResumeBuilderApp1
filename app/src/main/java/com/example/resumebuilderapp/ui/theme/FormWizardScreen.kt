package com.example.resumebuilderapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resumebuilderapp.data.Education
import com.example.resumebuilderapp.data.PersonalInfo
import com.example.resumebuilderapp.data.ResumeData
import com.example.resumebuilderapp.data.WorkExperience
import com.example.resumebuilderapp.ui.theme.PdfPreviewScreen
import com.example.resumebuilderapp.ui.theme.ResumeBuilderAppTheme

enum class WizardStep(val title: String) {
    PERSONAL("Personal"),
    EXPERIENCE("Experience"),
    EDUCATION("Education"),
    SKILLS("Skills"),
    EXTRA("Extra")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormWizardScreen(
    resumeData: ResumeData,
    onPersonalInfoChange: (PersonalInfo) -> Unit,
    onAddWorkExperience: (WorkExperience) -> Unit,
    onUpdateWorkExperience: (Int, WorkExperience) -> Unit,
    onRemoveWorkExperience: (Int) -> Unit,
    onAddEducation: (Education) -> Unit,
    onUpdateEducation: (Int, Education) -> Unit,
    onRemoveEducation: (Int) -> Unit,
    onSkillsChange: (List<String>) -> Unit,
    onFinish: () -> Unit,
    onBackClick: () -> Unit
) {
    var currentStep by remember { mutableStateOf(WizardStep.PERSONAL) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentStep.title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Text("Back") }
                }
            )
        },
        bottomBar = {
            Surface(tonalElevation = 4.dp, modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (currentStep.ordinal > 0) {
                        OutlinedButton(onClick = {
                            currentStep = WizardStep.entries[currentStep.ordinal - 1]
                        }) { Text("Previous") }
                    } else {
                        Spacer(modifier = Modifier.width(1.dp))
                    }

                    Button(onClick = {
                        if (currentStep.ordinal < WizardStep.entries.size - 1) {
                            currentStep = WizardStep.entries[currentStep.ordinal + 1]
                        } else {
                            onFinish()
                        }
                    }) {
                        Text(if (currentStep == WizardStep.EXTRA) "Save & Arrange" else "Next")
                    }
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
            ScrollableTabRow(selectedTabIndex = currentStep.ordinal, edgePadding = 0.dp) {
                WizardStep.entries.forEachIndexed { index, step ->
                    Tab(
                        selected = currentStep == step,
                        onClick = { currentStep = step },
                        text = { Text("${index + 1}. ${step.title}", fontSize = 12.sp) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                when (currentStep) {
                    WizardStep.PERSONAL -> PersonalInfoForm(
                        personalInfo = resumeData.personalInfo,
                        onInfoChanged = onPersonalInfoChange
                    )

                    WizardStep.EXPERIENCE -> ExperienceListForm(
                        experiences = resumeData.workExperiences,
                        onAddExperience = { onAddWorkExperience(WorkExperience()) },
                        onUpdateExperience = onUpdateWorkExperience,
                        onRemoveExperience = onRemoveWorkExperience
                    )

                    WizardStep.EDUCATION -> EducationListForm(
                        educationList = resumeData.educationList,
                        onAddEducation = { onAddEducation(Education()) },
                        onUpdateEducation = onUpdateEducation,
                        onRemoveEducation = onRemoveEducation
                    )

                    WizardStep.SKILLS -> SkillsForm(
                        skills = resumeData.skills,
                        onSkillsChanged = onSkillsChange
                    )

                    WizardStep.EXTRA -> ExtraSectionsForm()
                }
            }
        }
    }
}

// --- 1. PERSONAL INFO FORM ---
@Composable
fun PersonalInfoForm(
    personalInfo: PersonalInfo,
    onInfoChanged: (PersonalInfo) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {
            Surface(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {}
            SmallFloatingActionButton(onClick = {}) {
                Icon(Icons.Default.CameraAlt, contentDescription = "Upload Photo")
            }
        }

        OutlinedTextField(
            value = personalInfo.fullName,
            onValueChange = { onInfoChanged(personalInfo.copy(fullName = it)) },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = personalInfo.professionalTitle,
            onValueChange = { onInfoChanged(personalInfo.copy(professionalTitle = it)) },
            label = { Text("Professional Title") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = personalInfo.email,
            onValueChange = { onInfoChanged(personalInfo.copy(email = it)) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = personalInfo.phone,
            onValueChange = { onInfoChanged(personalInfo.copy(phone = it)) },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// --- 2. EXPERIENCE FORM ---
@Composable
fun ExperienceListForm(
    experiences: List<WorkExperience>,
    onAddExperience: () -> Unit,
    onUpdateExperience: (Int, WorkExperience) -> Unit,
    onRemoveExperience: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        experiences.forEachIndexed { index, exp ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Experience #${index + 1}", style = MaterialTheme.typography.titleMedium)
                        IconButton(onClick = { onRemoveExperience(index) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remove")
                        }
                    }
                    ExperienceForm(
                        experience = exp,
                        onExperienceChanged = { updated -> onUpdateExperience(index, updated) }
                    )
                }
            }
        }

        OutlinedButton(
            onClick = onAddExperience,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Work Experience")
        }
    }
}

@Composable
fun ExperienceForm(
    experience: WorkExperience,
    onExperienceChanged: (WorkExperience) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = experience.jobTitle,
            onValueChange = { onExperienceChanged(experience.copy(jobTitle = it)) },
            label = { Text("Job Title") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = experience.company,
            onValueChange = { onExperienceChanged(experience.copy(company = it)) },
            label = { Text("Company Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = experience.startDate,
                onValueChange = { onExperienceChanged(experience.copy(startDate = it)) },
                label = { Text("Start Date") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = experience.endDate,
                onValueChange = { onExperienceChanged(experience.copy(endDate = it)) },
                label = { Text("End Date") },
                enabled = !experience.isCurrentWork,
                modifier = Modifier.weight(1f)
            )
        }
        OutlinedTextField(
            value = experience.description,
            onValueChange = { onExperienceChanged(experience.copy(description = it)) },
            label = { Text("Description & Achievements") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// --- 3. EDUCATION FORM ---
@Composable
fun EducationListForm(
    educationList: List<Education>,
    onAddEducation: () -> Unit,
    onUpdateEducation: (Int, Education) -> Unit,
    onRemoveEducation: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        educationList.forEachIndexed { index, edu ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Education #${index + 1}", style = MaterialTheme.typography.titleMedium)
                        IconButton(onClick = { onRemoveEducation(index) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remove")
                        }
                    }
                    EducationForm(
                        education = edu,
                        onEducationChanged = { updated -> onUpdateEducation(index, updated) }
                    )
                }
            }
        }

        OutlinedButton(
            onClick = onAddEducation,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Education")
        }
    }
}

@Composable
fun EducationForm(
    education: Education,
    onEducationChanged: (Education) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = education.degree,
            onValueChange = { onEducationChanged(education.copy(degree = it)) },
            label = { Text("Degree / Certificate") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = education.institution,
            onValueChange = { onEducationChanged(education.copy(institution = it)) },
            label = { Text("University / School") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = education.graduationYear,
            onValueChange = { onEducationChanged(education.copy(graduationYear = it)) },
            label = { Text("Graduation Year") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun SkillsForm(
    skills: List<String>,
    onSkillsChanged: (List<String>) -> Unit
) {
    var newSkillText by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newSkillText,
                onValueChange = { newSkillText = it },
                label = { Text("Add Skill") },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    if (newSkillText.isNotBlank()) {
                        onSkillsChanged(skills + newSkillText.trim())
                        newSkillText = ""
                    }
                }
            ) {
                Text("Add")
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            skills.forEach { skill ->
                InputChip(
                    selected = false,
                    onClick = { onSkillsChanged(skills - skill) },
                    label = { Text(skill) },
                    trailingIcon = { Icon(Icons.Default.Delete, contentDescription = "Remove") }
                )
            }
        }
    }
}


@Composable
fun ExtraSectionsForm() {
    val sections = listOf("Projects", "Certifications", "Publications", "References", "Signature")
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        sections.forEach { section ->
            var checked by remember { mutableStateOf(false) }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(section)
                Switch(checked = checked, onCheckedChange = { checked = it })
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PdfPreviewScreenPreview() {
    val sampleResumeData = ResumeData(
        personalInfo = PersonalInfo(
            fullName = "John Doe",
            professionalTitle = "Android Developer",
            email = "john.doe@example.com",
            phone = "+1 234 567 890"
        ),
        workExperiences = listOf(
            WorkExperience(
                jobTitle = "Software Engineer",
                company = "Tech Corp",
                startDate = "2022",
                endDate = "Present",
                description = "Developing Compose apps."
            )
        ),
        educationList = listOf(
            Education(
                degree = "B.S. Computer Science",
                institution = "State University",
                graduationYear = "2021"
            )
        ),
        skills = listOf("Kotlin", "Jetpack Compose", "Android SDK")
    )
    ResumeBuilderAppTheme {
        FormWizardScreen(
            resumeData = sampleResumeData,
            onPersonalInfoChange = {},
            onAddWorkExperience = {},
            onUpdateWorkExperience = { _, _ -> },
            onRemoveWorkExperience = {},
            onAddEducation = {},
            onUpdateEducation = { _, _ -> },
            onRemoveEducation = {},
            onSkillsChange = {},
            onFinish = {},
            onBackClick = {}
        )
    }
}