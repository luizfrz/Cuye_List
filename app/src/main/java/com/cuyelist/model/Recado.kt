package com.cuyelist.model

enum class Priority(val label: String) {
    LOW("Baixa"),
    MEDIUM("Média"),
    HIGH("Alta"),
}

data class Recado(
    val id: Long,
    val title: String,
    val note: String = "",
    val priority: Priority = Priority.MEDIUM,
    val isDone: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
)

enum class RecadoFilter(val label: String) {
    ALL("Todos"),
    PENDING("Pendentes"),
    DONE("Concluídos"),
}

/**
 * Aplica busca (título e texto) e filtro de status, ordenando pendentes primeiro,
 * depois por prioridade (alta → baixa) e, por fim, do mais recente ao mais antigo.
 */
fun List<Recado>.visible(query: String, filter: RecadoFilter): List<Recado> {
    val term = query.trim()
    return filter { recado ->
        val matchesFilter = when (filter) {
            RecadoFilter.ALL -> true
            RecadoFilter.PENDING -> !recado.isDone
            RecadoFilter.DONE -> recado.isDone
        }
        matchesFilter && (
            term.isEmpty() ||
                recado.title.contains(term, ignoreCase = true) ||
                recado.note.contains(term, ignoreCase = true)
            )
    }.sortedWith(
        compareBy<Recado> { it.isDone }
            .thenByDescending { it.priority.ordinal }
            .thenByDescending { it.createdAt },
    )
}
