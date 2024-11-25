package com.skymilk.socialapp.android

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import com.skymilk.socialapp.android.presentation.common.datastore.UserSettings
import com.skymilk.socialapp.android.presentation.common.datastore.toAuthResultData
import kotlinx.coroutines.flow.map

class MainViewModel(
    private val dataStore: DataStore<UserSettings>
) : ViewModel() {

    val authState = dataStore.data.map {
        it.toAuthResultData().token
    }

}