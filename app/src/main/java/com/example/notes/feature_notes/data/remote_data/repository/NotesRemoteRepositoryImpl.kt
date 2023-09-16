package com.example.notes.feature_notes.data.remote_data.repository

import android.util.Log
import com.example.notes.feature_notes.domain.model.RemoteContentNoteModel
import com.example.notes.feature_notes.domain.model.RemoteNoteModel
import com.example.notes.feature_notes.domain.repository.NotesRemoteRepository
import com.example.notes.feature_notes.domain.unit.Resource
import com.example.notes.feature_notes.presentation.auth
import com.example.notes.feature_profile.domain.unit.ValidationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder

class NotesRemoteRepositoryImpl(): NotesRemoteRepository {

    companion object {
        private val ref = Firebase.database.reference
        private val auth = FirebaseAuth.getInstance()
        private const val TAG = "NotesRemoteRepositoryImpl_TAG"
    }

    override suspend fun takeAllNotes(): Resource<List<RemoteNoteModel>> {
        val userId = auth.uid
        val isUserId = userId != null

        if (isUserId) {
            val remoteNoteList = mutableListOf<RemoteNoteModel>()
            val result = ref.child(userId!!)
                .child("notes")
                .get()
                .await()

            for (data in result.children) {
                val note = RemoteNoteModel(
                    id = data.key.toString(),
                    value = RemoteContentNoteModel(
                        title = data.child("title").getValue().toString(),
                        content = data.child("content").getValue().toString(),
                        updateTime = data.child("updateTime").getValue().toString().toLong(),
                        isDeleted = data.child("isDeleted").getValue().toString().toBoolean()
                    )
                )
                remoteNoteList.add(
                    note
                )
            }

            return Resource.Success(
                data = remoteNoteList
            )
        }


        Log.d("check after", "after")

        return Resource.Error(
            message = "Problem with taking userId"
        )
    }

    override suspend fun uploadNotes(remoteNoteModel: RemoteNoteModel): ValidationResult {
        val userId = auth.uid

        if (userId != null) {
            val result = ref.child(userId)
                            .child("notes")
                            .child(remoteNoteModel.id)
                            .setValue(remoteNoteModel.value)

            return ValidationResult(
                successful = result.isSuccessful,
                errorMessage = result.exception?.message
            )
        }

        return ValidationResult(
            successful = false,
            errorMessage = "Problem with taking userId"
        )
    }

    override fun setUpSynchronize(isSynchronized: Boolean): ValidationResult {
        val idUser = auth.uid

        if (idUser != null) {
            val result = ref.child(idUser)
                .child("isSynchronized")
                .setValue(isSynchronized)

            return ValidationResult(
                successful = result.isSuccessful,
                errorMessage = result.exception?.message
            )
        }

        return ValidationResult(
            successful = false,
            errorMessage = "Problem with idUser"
        )
    }

    override suspend fun checkIsSynchronize(): Resource<Boolean> {
        val idUser = auth.uid

        if (idUser != null) {
            val result = ref.child(idUser)
                .child("isSynchronized")
                .get()
                .await()

            val value = result.value to Boolean
            val isSynchronized: Boolean? = value.first as Boolean?

            if(isSynchronized != null) {
                return Resource.Success(
                    data = isSynchronized
                )
            } else {
                return Resource.Error(
                    message = "Unknown Error"
                )
            }
        }

        return Resource.Error(
            message = "Problem with idUser"
        )
    }

    override suspend fun deleteNote(remoteNoteModel: RemoteNoteModel): ValidationResult {
        return try {
            val idUser = auth.uid

            if (idUser != null) {
                var isDeleted = false

                ref.child(idUser)
                    .child("notes")
                    .child(remoteNoteModel.id)
                    .child("isDeleted")
                    .setValue(true)
                    .addOnSuccessListener {
                        isDeleted = true
                    }
                    .await()

                delay(500)

                if(!isDeleted) {
                    return ValidationResult(
                        successful = false,
                        errorMessage = "Problem with deleting note!"
                    )
                }

                ValidationResult(
                    successful = true,
                    errorMessage = null
                )
            } else {
                ValidationResult(
                    successful = false,
                    errorMessage = "Problem taking dataUser"
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
}