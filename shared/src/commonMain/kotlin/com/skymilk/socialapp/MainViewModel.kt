package com.skymilk.socialapp

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.socialapp.data.model.UserSettings
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    private val dataStore: DataStore<UserSettings>
) : ViewModel() {

    val authState: StateFlow<MainAuthState> = dataStore.data.map { userSetting ->
        MainAuthState.Success(userSetting)
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainAuthState.Loading,
        started = SharingStarted.WhileSubscribed(5000)
    )
}