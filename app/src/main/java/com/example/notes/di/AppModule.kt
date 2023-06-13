package com.example.notes.di

import android.app.Application
import androidx.room.Room
import com.example.notes.core.model.ProfileModel
import com.example.notes.feature_notes.domain.use_case.*
import com.example.notes.feature_notes.data.local_data.data_source.NotesDatabase
import com.example.notes.feature_notes.data.local_data.repository.NotesRepositoryImpl
import com.example.notes.feature_notes.data.remote_data.repository.NotesRemoteRepositoryImpl
import com.example.notes.feature_notes.domain.repository.NoteRepository
import com.example.notes.feature_notes.domain.repository.NotesRemoteRepository
import com.example.notes.feature_notes.domain.use_case.local.DeleteNoteUseCase
import com.example.notes.feature_notes.domain.use_case.local.GetAllNotesUseCase
import com.example.notes.feature_notes.domain.use_case.local.GetNoteByIdUseCase
import com.example.notes.feature_notes.domain.use_case.local.InsertNoteUseCase
import com.example.notes.feature_notes.domain.use_case.local.NotesUseCases
import com.example.notes.feature_notes.domain.use_case.remote.CheckIsSynchronize
import com.example.notes.feature_notes.domain.use_case.remote.RemoteUseCases
import com.example.notes.feature_notes.domain.use_case.remote.SetUpSynchronizeUseCase
import com.example.notes.feature_notes.domain.use_case.remote.TakeAllNotesUseCase
import com.example.notes.feature_notes.domain.use_case.remote.UploadNoteUseCase
import com.example.notes.feature_profile.data.repository.ProfileRepositoryImpl
import com.example.notes.feature_profile.domain.repository.ProfileRepository
import com.example.notes.feature_profile.domain.use_case.profileUseCases.ChangeEmailUseCase
import com.example.notes.feature_profile.domain.use_case.profileUseCases.ChangePasswordUseCase
import com.example.notes.feature_profile.domain.use_case.profileUseCases.ForgetPasswordUseCase
import com.example.notes.feature_profile.domain.use_case.profileUseCases.LogInUseCase
import com.example.notes.feature_profile.domain.use_case.profileUseCases.ProfileUseCases
import com.example.notes.feature_profile.domain.use_case.profileUseCases.RegistrationUseCase
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateEmail
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidatePassword
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateRePassword
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateTerms
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNotesDatabase(app: Application): NotesDatabase {
        return Room.databaseBuilder(
            app,
            NotesDatabase::class.java,
            NotesDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNotesRepository(db: NotesDatabase): NoteRepository {
        return NotesRepositoryImpl(db.notesDao)
    }

    @Provides
    @Singleton
    fun provideNotesUseCases(repository: NoteRepository): NotesUseCases {
        return NotesUseCases(
            getAllNotesUseCase = GetAllNotesUseCase(repository),
            getNoteByIdUseCase = GetNoteByIdUseCase(repository),
            insertNoteUseCase = InsertNoteUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideValidateUseCases(): ValidateUseCases {
        return ValidateUseCases(
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validateRePassword = ValidateRePassword(),
            validateTerms = ValidateTerms()
        )
    }

    @Provides
    @Singleton
    fun provideProfileRepository(): ProfileRepository {
        return ProfileRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: ProfileRepository): ProfileUseCases {
        return ProfileUseCases(
            logInUseCase = LogInUseCase(repository),
            registrationUseCase = RegistrationUseCase(repository),
            forgetPasswordUseCase = ForgetPasswordUseCase(repository),
            changeEmailUseCase = ChangeEmailUseCase(repository),
            changePasswordUseCase = ChangePasswordUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideNotesRemoteRepository(): NotesRemoteRepository {
        return NotesRemoteRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideRemoteUseCases(repository: NotesRemoteRepository): RemoteUseCases {
        return RemoteUseCases(
            takeAllNotesUseCase = TakeAllNotesUseCase(repository),
            uploadNoteUseCase = UploadNoteUseCase(repository),
            checkIsSynchronize = CheckIsSynchronize(repository),
            setUpSynchronizeUseCase = SetUpSynchronizeUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideProfileModel(): ProfileModel {
        return ProfileModel()
    }
}