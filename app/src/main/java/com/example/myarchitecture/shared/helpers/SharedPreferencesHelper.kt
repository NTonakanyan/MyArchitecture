package com.example.myarchitecture.shared.helpers

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesHelper constructor(context: Context) {

    val sharedPreferences: SharedPreferences
    private val mEditor: SharedPreferences.Editor

    init {
        sharedPreferences = context.getSharedPreferences("Configs", Context.MODE_PRIVATE)
        mEditor = sharedPreferences.edit()
    }

    fun setStringSharedPreferences(key: String, value: String?) {
        mEditor.putString(key, value)
        mEditor.commit()
    }

    fun getStringSharedPreferences(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun setIntSharedPreferences(key: String, value: Int) {
        mEditor.putInt(key, value)
        mEditor.commit()
    }

    fun getIntSharedPreferences(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun setBooleanSharedPreferences(key: String, value: Boolean?) {
        mEditor.putBoolean(key, value!!)
        mEditor.commit()
    }

    fun getBooleanSharedPreferences(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun setObjectSharedPreferences(key: String, list: List<String>) {
        val gson = Gson()
        val json = gson.toJson(list)
        mEditor.putString(key, json)
        mEditor.commit()
    }

    fun getObjectSharedPreferences(key: String): List<String> {
        val gson = Gson()
        val json = sharedPreferences.getString(key, null)
        return gson.fromJson(json, object : TypeToken<List<String>>() {}.getType())
    }

    fun deleteSharedPreferences() {
        mEditor.clear()
        mEditor.commit()
    }
}