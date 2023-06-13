package com.example.notes.feature_notes.domain.use_case.remote

data class RemoteUseCases(
    val takeAllNotesUseCase: TakeAllNotesUseCase,
    val uploadNoteUseCase: UploadNoteUseCase,
    val setUpSynchronizeUseCase: SetUpSynchronizeUseCase,
    val checkIsSynchronize: CheckIsSynchronize
)
