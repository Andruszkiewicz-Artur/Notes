package com.example.notes.feature_profile.presentation.changeEmail

import android.app.Application
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangeEmailViewModel @Inject constructor(
    private val application: Application
): ViewModel() {

}