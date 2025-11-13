package com.shoaib.weatherapp.utils

import android.content.Context
import android.util.Base64
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.security.SecureRandom


object KeyStoreUtil {

    private const val PREF_NAME = "secure_prefs"

    private const val KEY_NAME = "db_passphrase"


    fun getOrCreatePassphrase(context: Context): ByteArray {

        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val prefs = EncryptedSharedPreferences.create(
            context,
            PREF_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )


        val existingKey = prefs.getString(KEY_NAME, null)

        return if (existingKey != null) {

            Base64.decode(existingKey, Base64.DEFAULT)
        } else {

            val newKey = ByteArray(32)
            SecureRandom().nextBytes(newKey)


            prefs.edit().putString(
                KEY_NAME,
                Base64.encodeToString(newKey, Base64.DEFAULT)
            ).apply()

            newKey
        }
    }
}
