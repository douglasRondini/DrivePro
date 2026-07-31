package com.douglasrondini.drive_20_android.data.local

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveUserToken(token: String) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getUserToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    fun saveUserData(id: String, name: String, email: String, role: String) {
        sharedPreferences.edit().apply {
            putString(KEY_USER_ID, id)
            putString(KEY_USER_NAME, name)
            putString(KEY_USER_EMAIL, email)
            putString(KEY_USER_ROLE, role)
            apply()
        }
    }

    fun getUserRole(): String? {
        return sharedPreferences.getString(KEY_USER_ROLE, null)
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(KEY_USER_NAME, null)
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    fun saveInstructorStats(completed: Int, pending: Int, revenue: Double, accepted: Int) {
        sharedPreferences.edit().apply {
            putInt(KEY_COMPLETED_COUNT, completed)
            putInt(KEY_PENDING_COUNT, pending)
            putFloat(KEY_TOTAL_REVENUE, revenue.toFloat())
            putInt(KEY_ACCEPTED_COUNT, accepted)
            apply()
        }
    }

    fun getSavedCompletedCount() = sharedPreferences.getInt(KEY_COMPLETED_COUNT, 0)
    fun getSavedPendingCount() = sharedPreferences.getInt(KEY_PENDING_COUNT, 0)
    fun getSavedTotalRevenue() = sharedPreferences.getFloat(KEY_TOTAL_REVENUE, 0.0f).toDouble()
    fun getSavedAcceptedCount() = sharedPreferences.getInt(KEY_ACCEPTED_COUNT, 0)

    fun clearData() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private const val PREF_NAME = "DriveProPrefs"
        private const val KEY_TOKEN = "key_token"
        private const val KEY_USER_ID = "key_user_id"
        private const val KEY_USER_NAME = "key_user_name"
        private const val KEY_USER_EMAIL = "key_user_email"
        private const val KEY_USER_ROLE = "key_user_role"

        private const val KEY_COMPLETED_COUNT = "key_completed_count"
        private const val KEY_PENDING_COUNT = "key_pending_count"
        private const val KEY_TOTAL_REVENUE = "key_total_revenue"
        private const val KEY_ACCEPTED_COUNT = "key_accepted_count"
    }
}
