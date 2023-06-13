package com.example.notes.feature_notes.data.remote_data.repository

import android.util.Log
import com.example.notes.feature_notes.domain.model.RemoteNoteModel
import com.example.notes.feature_notes.domain.repository.NotesRemoteRepository
import com.example.notes.feature_notes.domain.unit.Resource
import com.example.notes.feature_notes.presentation.auth
import com.example.notes.feature_profile.domain.unit.ValidationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json

@Suppress("KotlinConstantConditions")
class NotesRemoteRepositoryImpl(): NotesRemoteRepository {

    companion object {
        val ref = Firebase.database.reference
        val auth = FirebaseAuth.getInstance()
    }

    override fun takeAllNotes(): Resource<List<RemoteNoteModel>> {
        return Resource.Success(
            data = emptyList()
        )
    }

    override fun uploadNotes(idUser: String, remoteNoteModel: RemoteNoteModel): ValidationResult {
        var errorMessage: String? = null

        val jsonValue = Json.parseToJsonElement(remoteNoteModel.toString())
        Log.d("Json result", jsonValue.toString())
        Log.d("String result", remoteNoteModel.toString())

//        database.child(idUser).child("notes").child(jsonValue.toString())

        return ValidationResult(
            successful = errorMessage == null,
            errorMessage = errorMessage
        )
    }

    override fun setUpSynchronize(isSynchronized: Boolean): ValidationResult {
        val idUser = auth.uid

        if (idUser != null) {
            val result = ref.child(idUser).child("isSynchronized").setValue(isSynchronized)

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
            val result = ref.child(idUser).child("isSynchronized").get().await()

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
}