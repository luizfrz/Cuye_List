package com.cuyelist.model

import org.junit.Assert.assertEquals
import org.junit.Test

class RecadoTest {
    private val recados = listOf(
        Recado(1, "Comprar ração", "para o cuye", Priority.LOW, createdAt = 1),
        Recado(2, "Pagar conta", "", Priority.HIGH, createdAt = 2),
        Recado(3, "Limpar gaiola", "", Priority.HIGH, isDone = true, createdAt = 3),
        Recado(4, "Ligar para o vet", "", Priority.HIGH, createdAt = 4),
    )

    @Test
    fun ordersPendingFirstThenPriorityThenNewest() {
        val ids = recados.visible("", RecadoFilter.ALL).map { it.id }
        assertEquals(listOf(4L, 2L, 1L, 3L), ids)
    }

    @Test
    fun filtersByStatus() {
        assertEquals(listOf(3L), recados.visible("", RecadoFilter.DONE).map { it.id })
        assertEquals(3, recados.visible("", RecadoFilter.PENDING).size)
    }

    @Test
    fun searchesTitleAndNoteIgnoringCase() {
        assertEquals(listOf(1L), recados.visible("CUYE", RecadoFilter.ALL).map { it.id })
        assertEquals(listOf(2L), recados.visible(" conta ", RecadoFilter.ALL).map { it.id })
    }
}
