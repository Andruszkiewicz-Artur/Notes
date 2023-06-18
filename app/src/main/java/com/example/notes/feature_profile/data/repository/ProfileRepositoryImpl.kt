package com.example.notes.feature_profile.data.repository

import android.util.Log
import com.example.notes.R
import com.example.notes.feature_notes.presentation.auth
import com.example.notes.feature_profile.domain.repository.ProfileRepository
import com.example.notes.feature_profile.domain.unit.ValidationResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ProfileRepositoryImpl(): ProfileRepository {
    override suspend fun LogIn(email: String, password: String): ValidationResult {
        val result = auth.signInWithEmailAndPassword(email, password).await()

        return ValidationResult(
            successful = result.user != null,
            errorMessage = if (result.user != null) null else "Problem with logIn"
        )
    }

    override fun LogOut(): ValidationResult {
        val result = auth.signOut()

        return ValidationResult(
            successful = auth.currentUser == null,
            errorMessage = if (auth.currentUser == null) null else R.string.ProblemWithLogOut.toString()
        )
    }

    override fun Registration(email: String, password: String): ValidationResult {
        val result = auth.createUserWithEmailAndPassword(email, password)

        return ValidationResult(
            successful = result.isSuccessful,
            errorMessage = result.exception?.message
        )
    }

    override fun ForgeinPassword(email: String): ValidationResult {
        var errorMessage: String? = null

        Firebase.auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    errorMessage = R.string.ProblemWithDatabase.toString()
                }
            }

        return ValidationResult(
            successful = errorMessage == null,
            errorMessage = errorMessage
        )
    }

    override fun ChangePassword(
        user: FirebaseUser,
        email: String,
        oldPassword: String,
        newPassword: String
    ): ValidationResult {
        var errorMessage: String? = null
        val credential = EmailAuthProvider
            .getCredential(email, oldPassword)

        user.reauthenticate(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user.updatePassword(newPassword)
                        .addOnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                errorMessage = R.string.ProblemWithDatabase.toString()
                            }
                        }
                } else {
                    errorMessage = R.string.OldPasswordIsWrong.toString()
                }
            }

        return ValidationResult(
            successful = errorMessage == null,
            errorMessage = errorMessage
        )
    }

    override fun ChangeEmail(
        user: FirebaseUser,
        oldEmail: String,
        newEmail: String,
        password: String
    ): ValidationResult {
        var errorMessage: String? = null
        val credential = EmailAuthProvider
            .getCredential(oldEmail, password)

        user.reauthenticate(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user.updateEmail(newEmail)
                        .addOnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                errorMessage = R.string.OldPasswordIsWrong.toString()
                            }
                        }
                } else {
                    errorMessage = R.string.WrongPassword.toString()
                }
            }

        return ValidationResult(
            successful = errorMessage == null,
            errorMessage = errorMessage
        )
    }
}