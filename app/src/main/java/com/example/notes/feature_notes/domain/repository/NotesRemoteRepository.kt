package com.example.notes.feature_notes.domain.repository

import com.example.notes.feature_notes.domain.model.RemoteNoteModel
import com.example.notes.feature_notes.domain.unit.Resource
import com.example.notes.feature_profile.domain.unit.ValidationResult

interface NotesRemoteRepository {

    suspend fun takeAllNotes(): Resource<List<RemoteNoteModel>>

    suspend fun uploadNotes(remoteNoteModel: RemoteNoteModel): ValidationResult

    fun setUpSynchronize(isSynchronized: Boolean): ValidationResult

    suspend fun checkIsSynchronize(): Resource<Boolean>
}