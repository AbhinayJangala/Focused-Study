package com.example.foucsedstudyapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foucsedstudyapp.repository.FocusRepository
import com.example.foucsedstudyapp.ui.screens.focus.InstalledApp
import com.example.foucsedstudyapp.ui.screens.focus.getInstalledApps
import com.example.foucsedstudyapp.utils.FocusManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.foucsedstudyapp.data.focusSession
import com.example.foucsedstudyapp.repository.HomeRepository
import com.google.firebase.auth.FirebaseAuth
class FocusViewModel : ViewModel() {
    private val _installedApps = MutableStateFlow<List<InstalledApp>>(emptyList())
    val installedApps: StateFlow<List<InstalledApp>> = _installedApps.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val homeRepository = HomeRepository()
    private val focusRepository = FocusRepository()
    fun loadApps(context: Context) {
        if (_installedApps.value.isNotEmpty()) return
        
        viewModelScope.launch {
            _isLoading.value = true
            val apps = withContext(Dispatchers.IO) {
                getInstalledApps(context)
            }
            _installedApps.value = apps
            _isLoading.value = false
        }
    }

    fun startFocusSession(context: Context, apps: Set<String>, minutes: Int) {
        FocusManager(context).startFocus(apps, minutes)
    }

    fun stopFocusSession(context: Context) {
        FocusManager(context).stopFocus()
    }
    fun completeFocusSession(
        context: Context,
        durationMinutes: Int,
        onComplete: () -> Unit
    ) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId == null) {
            onComplete()
            return
        }

        val endTime = System.currentTimeMillis()
        val startTime = endTime - (durationMinutes * 60 * 1000L)

        val session = focusSession(
            userId = userId,
            durationMinutes = durationMinutes,
            startTime = startTime,
            endTime = endTime,
            completed = true
        )

        focusRepository.saveFocusSession(
            session = session,
            onSuccess = {
                android.util.Log.d("FocusViewModel", "Focus session saved successfully")
                homeRepository.updateStudyStats(
                    studyTimeMinutes = durationMinutes.toLong(),
                    onSuccess = {
                        android.util.Log.d("FocusViewModel", "Study stats updated successfully")
                        onComplete()
                    },
                    onFailure = { error ->
                        android.util.Log.e("FocusViewModel", "Failed to update study stats: $error")
                        onComplete()
                    }
                )

            },
            onFailure = { error ->
                android.util.Log.e("FocusViewModel", "Failed to save focus session: $error")
                onComplete()
            }
        )
    }

}