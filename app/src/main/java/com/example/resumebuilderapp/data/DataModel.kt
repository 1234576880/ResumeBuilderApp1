package com.example.resumebuilderapp.data

data class PersonalInfo(
    val fullName: String = "",
    val professionalTitle: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val website: String = ""
)

data class WorkExperience(
    val id: String = java.util.UUID.randomUUID().toString(),
    val jobTitle: String = "",
    val company: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val isCurrentWork: Boolean = false,
    val description: String = ""
)

data class Education(
    val id: String = java.util.UUID.randomUUID().toString(),
    val degree: String = "",
    val institution: String = "",
    val graduationYear: String = ""
)

data class ResumeData(
    val id: String = java.util.UUID.randomUUID().toString(),
    val resumeTitle: String = "Untitled Resume",
    val selectedTemplateId: String = "1",
    val personalInfo: PersonalInfo = PersonalInfo(),
    val workExperiences: List<WorkExperience> = emptyList(),
    val educationList: List<Education> = emptyList(),
    val skills: List<String> = emptyList()
)