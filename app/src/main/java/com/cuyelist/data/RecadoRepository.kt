package com.cuyelist.data

import android.content.Context
import com.cuyelist.model.Priority
import com.cuyelist.model.Recado
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/** Persiste os recados localmente em SharedPreferences, serializados como JSON. */
class RecadoRepository(context: Context) {
    private val prefs = context.applicationContext
        .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun load(): List<Recado> {
        val raw = prefs.getString(KEY_RECADOS, null) ?: return emptyList()
        return try {
            val array = JSONArray(raw)
            List(array.length()) { index -> array.getJSONObject(index).toRecado() }
        } catch (_: JSONException) {
            emptyList()
        }
    }

    fun save(recados: List<Recado>) {
        val array = JSONArray()
        recados.forEach { array.put(it.toJson()) }
        prefs.edit().putString(KEY_RECADOS, array.toString()).apply()
    }

    private fun Recado.toJson() = JSONObject()
        .put("id", id)
        .put("title", title)
        .put("note", note)
        .put("priority", priority.name)
        .put("isDone", isDone)
        .put("createdAt", createdAt)

    private fun JSONObject.toRecado() = Recado(
        id = getLong("id"),
        title = getString("title"),
        note = optString("note"),
        priority = runCatching { Priority.valueOf(optString("priority")) }
            .getOrDefault(Priority.MEDIUM),
        isDone = optBoolean("isDone"),
        createdAt = optLong("createdAt"),
    )

    private companion object {
        const val PREFS_NAME = "recado_prefs"
        const val KEY_RECADOS = "recados"
    }
}
