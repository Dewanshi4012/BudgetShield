package com.example.budgetshield.data

import android.content.Context
import android.content.SharedPreferences

class SharedPref (context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    // Save email and name
    fun saveUserDetails(email: String?, name: String?) {
        sharedPreferences.edit().apply {
            putString("userEmail", email)
            putString("userName", name)
            putBoolean("isLoggedIn", true)
            apply()
        }
    }

    // Retrieve email
    fun getUserEmail(): String? {
        return sharedPreferences.getString("userEmail", "No Email")
    }

    // Retrieve name
    fun getUserName(): String? {
        return sharedPreferences.getString("userName", "No Name")
    }

    // Check login status
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    // Clear all saved data (e.g., during logout)
    fun clearData() {
        sharedPreferences.edit().clear().apply()
    }
}
