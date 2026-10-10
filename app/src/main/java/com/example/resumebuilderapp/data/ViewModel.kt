package com.example.resumebuilderapp.data

import androidx.lifecycle.ViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ResumeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ResumeData())
    val uiState: StateFlow<ResumeData> = _uiState.asStateFlow()


    fun updatePersonalInfo(info: PersonalInfo) {
        _uiState.update { it.copy(personalInfo = info) }
    }

    fun addWorkExperience(experience: WorkExperience) {
        _uiState.update { currentState ->
            currentState.copy(workExperiences = currentState.workExperiences + experience)
        }
    }

    fun updateWorkExperience(index: Int, experience: WorkExperience) {
        _uiState.update { currentState ->
            val updatedList = currentState.workExperiences.toMutableList()
            if (index in updatedList.indices) {
                updatedList[index] = experience
            }
            currentState.copy(workExperiences = updatedList)
        }
    }

    fun removeWorkExperience(index: Int) {
        _uiState.update { currentState ->
            val updatedList = currentState.workExperiences.toMutableList()
            if (index in updatedList.indices) {
                updatedList.removeAt(index)
            }
            currentState.copy(workExperiences = updatedList)
        }
    }


    fun addEducation(education: Education) {
        _uiState.update { currentState ->
            currentState.copy(educationList = currentState.educationList + education)
        }
    }

    fun updateEducation(index: Int, education: Education) {
        _uiState.update { currentState ->
            val updatedList = currentState.educationList.toMutableList()
            if (index in updatedList.indices) {
                updatedList[index] = education
            }
            currentState.copy(educationList = updatedList)
        }
    }

    fun removeEducation(index: Int) {
        _uiState.update { currentState ->
            val updatedList = currentState.educationList.toMutableList()
            if (index in updatedList.indices) {
                updatedList.removeAt(index)
            }
            currentState.copy(educationList = updatedList)
        }
    }


    fun updateSkills(skills: List<String>) {
        _uiState.update { it.copy(skills = skills) }
    }

    fun selectTemplate(templateId: String) {
        _uiState.update { it.copy(selectedTemplateId = templateId) }
    }
}