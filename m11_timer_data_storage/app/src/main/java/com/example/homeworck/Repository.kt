package com.example.homeworck

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

private const val KEY_TEXT = "keyText"
private const val NAME_PREF = "pref"
private const val FILE_NAME = "fileName"

class Repository(private val context: Context) {
    private var pref: SharedPreferences =
        context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE)

    fun saveText(text: String) {
        if (text.isNotEmpty()) {
            pref.edit()?.putString(KEY_TEXT, "$text from shared")?.apply()
            saveToLocalFile("$text from file")
        }
    }

    private fun saveToLocalFile(text: String) {
        var fileOut: FileOutputStream? = null
        try {
            fileOut = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            fileOut.write(text.toByteArray())
        } catch (e: IOException) {
            Toast.makeText(context, "error ${e.message}", Toast.LENGTH_SHORT).show()
        } finally {
            fileOut?.close()
        }
    }

    fun clearText() {
        pref.edit().remove(KEY_TEXT).apply()
    }

    fun getText(): String {
        return when {
            getDataFromSharedPreferences() != null -> getDataFromSharedPreferences()!!
            getDataFromLocalVariable() != null -> getDataFromLocalVariable()!!
            else -> "Text not found"
        }
    }

    private fun getDataFromLocalVariable(): String? {
        var fileInput: FileInputStream? = null
        return try {
            fileInput = context.openFileInput(FILE_NAME)
            val bytes = ByteArray(fileInput.available())
            fileInput.read(bytes)
            String(bytes)
        } catch (e: IOException) {
            null
        } finally {
            fileInput?.close()
        }
    }

    private fun getDataFromSharedPreferences() = pref.getString(KEY_TEXT, null)
}