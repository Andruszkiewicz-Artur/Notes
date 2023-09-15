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
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

class ProfileRepositoryImpl(): ProfileRepository {

    companion object {
        private const val TAG = "ProfileRepositoryImpl"
    }

    override suspend fun LogIn(email: String, password: String): ValidationResult {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()

            delay(500)

            if (result.user != null) {
                ValidationResult(
                    successful = true,
                    errorMessage = null
                )
            } else {
                ValidationResult(
                    successful = false,
                    errorMessage = "Problem with logIn"
                )
            }
        } catch (e: Exception) {
            Log.d(TAG, "${e.message}")

            ValidationResult(
                successful = false,
                errorMessage = "${e.message}"
            )
        }
    }

    override fun LogOut(): ValidationResult {
        val result = auth.signOut()

        return ValidationResult(
            successful = auth.currentUser == null,
            errorMessage = if (auth.currentUser == null) null else R.string.ProblemWithLogOut.toString()
        )
    }

    override suspend fun Registration(email: String, password: String): ValidationResult {
        return try {
            var result = false
            val requestResult = auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    result = true
            }.await()

            delay(500)

            Log.d(TAG, "${requestResult}")

            if (result) {
                ValidationResult(
                    successful = true,
                    errorMessage = null
                )
            } else {
                ValidationResult(
                    successful = false,
                    errorMessage = "Problem with register"
                )
            }
        } catch (e: Exception) {
            Log.d(TAG, "${e.message}")

            ValidationResult(
                successful = false,
                errorMessage = "${e.message}"
            )
        }
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