package com.example.notes.feature_profile.domain.repository

import com.example.notes.feature_profile.domain.unit.ValidationResult
import com.google.firebase.auth.FirebaseUser

interface ProfileRepository {

    suspend fun LogIn(email: String, password: String): ValidationResult

    fun LogOut(): ValidationResult

    suspend fun Registration(email: String, password: String): ValidationResult

    suspend fun ForgeinPassword(email: String): ValidationResult

    suspend fun ChangePassword(oldPassword: String, newPassword: String): ValidationResult

    suspend fun ChangeEmail(newEmail: String, password: String): ValidationResult
}