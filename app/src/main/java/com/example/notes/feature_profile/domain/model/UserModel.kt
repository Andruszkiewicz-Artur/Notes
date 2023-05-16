package com.example.notes.feature_profile.domain.model

import com.google.firebase.database.Exclude

data class UserModel(
    val userId: String,
    val isSaveDataInCloud: Boolean,
    val notes: List<NoteModel>
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "userId" to userId,
            "isSaveDataInCloud" to isSaveDataInCloud,
            "notes" to notes
        )
    }
}

data class NoteModel(
    val noteId: Double,
    val title: String,
    val content: String
) {

}
