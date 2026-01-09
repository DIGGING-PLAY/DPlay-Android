package com.example.data.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun saveUser(user: User) {
        dataStore.edit { prefs ->
            prefs[USER_ID] = user.id.toString()
            prefs[NICKNAME] = user.nickname
            prefs[PROFILE_IMAGE] = user.profileImageUri.orEmpty()
        }
    }

    val userFlow: Flow<User?> = dataStore.data.map { prefs ->
        val id = prefs[USER_ID] ?: return@map null
        User(
            id = id.toLong(),
            nickname = prefs[NICKNAME].orEmpty(),
            profileImageUri = prefs[PROFILE_IMAGE],
        )
    }

    companion object {
        private val USER_ID = stringPreferencesKey("user_id")
        private val NICKNAME = stringPreferencesKey("nickname")
        private val PROFILE_IMAGE = stringPreferencesKey("profile_image")
    }
}
