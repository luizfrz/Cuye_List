package com.cuyelist.ui.screens.recado

import android.app.Application
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cuyelist.data.RecadoRepository
import com.cuyelist.model.Priority
import com.cuyelist.model.Recado
import com.cuyelist.model.RecadoFilter
import com.cuyelist.model.visible

class RecadoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RecadoRepository(application)

    var recados by mutableStateOf(repository.load())
        private set
    var query by mutableStateOf("")
    var filter by mutableStateOf(RecadoFilter.ALL)

    val visibleRecados by derivedStateOf { recados.visible(query, filter) }
    val doneCount by derivedStateOf { recados.count { it.isDone } }

    fun add(title: String, note: String, priority: Priority) {
        val id = (recados.maxOfOrNull { it.id } ?: 0L) + 1
        update(recados + Recado(id = id, title = title.trim(), note = note.trim(), priority = priority))
    }

    fun edit(id: Long, title: String, note: String, priority: Priority) {
        update(
            recados.map {
                if (it.id == id) it.copy(title = title.trim(), note = note.trim(), priority = priority) else it
            },
        )
    }

    fun toggle(id: Long) {
        update(recados.map { if (it.id == id) it.copy(isDone = !it.isDone) else it })
    }

    fun delete(recado: Recado) {
        update(recados.filterNot { it.id == recado.id })
    }

    fun restore(recado: Recado) {
        if (recados.none { it.id == recado.id }) update(recados + recado)
    }

    /** Remove os concluídos e devolve a lista removida, para permitir desfazer. */
    fun clearDone(): List<Recado> {
        val removed = recados.filter { it.isDone }
        update(recados - removed.toSet())
        return removed
    }

    fun restoreAll(removed: List<Recado>) {
        update(recados + removed.filter { r -> recados.none { it.id == r.id } })
    }

    private fun update(newList: List<Recado>) {
        recados = newList
        repository.save(newList)
    }
}
