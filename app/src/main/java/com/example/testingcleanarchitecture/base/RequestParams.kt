package com.example.testingcleanarchitecture.base

import android.os.Parcelable

class RequestParams {
    val parameters: HashMap<String, Any> = HashMap()

    fun putInt(key: String, value: Int) {
        parameters[key] = value
    }

    fun putString(key: String, value: String) {
        parameters[key] = value
    }

    fun putBoolean(key: String, value: Boolean) {
        parameters[key] = value
    }

    fun putLong(key: String, value: Long) {
        parameters[key] = value
    }

    fun putDouble(key: String, value: Double) {
        parameters[key] = value
    }

    fun putObject(key: String, `object`: Any) {
        parameters[key] = `object`
    }

    fun <T : Parcelable> putParcelable(key: String, value: T) {
        parameters[key] = value
    }

    fun <T : Parcelable> getParcelable(key: String) : T? {
        val `object` = parameters[key]
        return try {
            `object` as T
        } catch (e: ClassCastException) {
            null
        }
    }

    fun getInt(key: String?, defaultValue: Int): Int {
        val `object` = parameters[key] ?: return defaultValue
        return try {
            `object` as Int
        } catch (e: ClassCastException) {
            defaultValue
        }
    }

    fun getString(key: String?, defaultValue: String): String {
        val `object` = parameters[key] ?: return defaultValue
        return try {
            `object` as String
        } catch (e: ClassCastException) {
            defaultValue
        }
    }

    fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
        val `object` = parameters[key] ?: return defaultValue
        return try {
            `object` as Boolean
        } catch (e: ClassCastException) {
            defaultValue
        }
    }

    fun getLong(key: String?, defaultValue: Long): Long {
        val `object` = parameters[key] ?: return defaultValue
        return try {
            `object` as Long
        } catch (e: ClassCastException) {
            defaultValue
        }
    }

    fun getDouble(key: String?, defaultValue: Double): Double {
        val `object` = parameters[key] ?: return defaultValue
        return try {
            `object` as Double
        } catch (e: ClassCastException) {
            defaultValue
        }
    }

    fun getObject(key: String?): Any? {
        return parameters[key]
    }

    fun clearValue(key: String?) {
        parameters.remove(key)
    }

    val paramsAllValueInString: HashMap<String, String>
        get() = convertMapObjectToString(parameters)

    private fun convertMapObjectToString(map: HashMap<String, Any>): HashMap<String, String> {
        val newMap: HashMap<String, String> = HashMap()
        map.forEach{key, value ->
            if (value is String) {
                newMap[key] = value.toString()
            }
        }
        return newMap
    }

    fun putAll(params: Map<String, Any>?) {
        parameters.putAll(params!!)
    }

    fun putAllString(params: Map<String, String>?) {
        parameters.putAll(params!!)
    }

    companion object {
        val EMPTY = create()
        fun create(): RequestParams {
            return RequestParams()
        }
    }
}
