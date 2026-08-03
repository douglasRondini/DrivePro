package com.douglasrondini.drive_20_android.ui.home

import androidx.lifecycle.ViewModel
import com.douglasrondini.drive_20_android.data.local.PreferenceManager

class PerfilAlunoViewModel(
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    fun getUserName(): String? {
        return preferenceManager.getUserName()
    }

    fun logout() {
        preferenceManager.clearData()
    }
}
