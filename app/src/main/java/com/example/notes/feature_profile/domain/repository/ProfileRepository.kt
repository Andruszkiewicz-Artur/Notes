package com.example.notes.feature_profile.domain.repository

import com.example.notes.feature_profile.domain.unit.ValidationResult
import com.google.firebase.auth.FirebaseUser

interface ProfileRepository {

    suspend fun LogIn(email: String, password: String): ValidationResult

    fun LogOut(): ValidationResult

    suspend fun Registration(email: String, password: String): ValidationResult

    fun ForgeinPassword(email: String): ValidationResult

    fun ChangePassword(user: FirebaseUser, email: String, oldPassword: String, newPassword: String): ValidationResult

    fun ChangeEmail(user: FirebaseUser,oldEmail: String, newEmail: String, password: String): ValidationResult
}