package com.cuyelist.ui.screens.recado

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.cuyelist.model.Recado
import com.cuyelist.model.RecadoFilter
import com.cuyelist.ui.theme.PriorityHigh
import com.cuyelist.ui.theme.RecadoNavy
import com.cuyelist.ui.theme.RecadoSurface
import kotlinx.coroutines.launch

@Composable
fun RecadoScreen(
    viewModel: RecadoViewModel,
    onBack: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // null = folha fechada; recado com id 0 = novo; demais = edição.
    var editing by remember { mutableStateOf<Recado?>(null) }

    fun showUndo(message: String, onUndo: () -> Unit) {
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            val result = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = "Desfazer",
                duration = SnackbarDuration.Short,
            )
            if (result == SnackbarResult.ActionPerformed) onUndo()
        }
    }

    fun share(recado: Recado) {
        val text = buildString {
            append(recado.title)
            if (recado.note.isNotBlank()) append("\n").append(recado.note)
        }
        val send = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        context.startActivity(Intent.createChooser(send, "Compartilhar recado"))
    }

    val recados = viewModel.recados
    val counts = mapOf(
        RecadoFilter.ALL to recados.size,
        RecadoFilter.PENDING to recados.size - viewModel.doneCount,
        RecadoFilter.DONE to viewModel.doneCount,
    )

    Scaffold(
        containerColor = RecadoSurface,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { editing = Recado(id = 0, title = "") },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Novo recado") },
                containerColor = RecadoNavy,
                contentColor = Color.White,
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding()),
        ) {
            RecadosHeader(
                total = recados.size,
                done = viewModel.doneCount,
                canClearDone = viewModel.doneCount > 0,
                onBack = onBack,
                onClearDone = {
                    val removed = viewModel.clearDone()
                    showUndo("${removed.size} concluído(s) removido(s)") { viewModel.restoreAll(removed) }
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchField(query = viewModel.query, onQueryChange = { viewModel.query = it })
            Spacer(modifier = Modifier.height(12.dp))
            FilterRow(
                selected = viewModel.filter,
                counts = counts,
                onSelect = { viewModel.filter = it },
            )
            Spacer(modifier = Modifier.height(12.dp))

            val items = viewModel.visibleRecados
            if (items.isEmpty()) {
                EmptyState(hasRecados = recados.isNotEmpty())
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 96.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(items, key = { it.id }) { recado ->
                        val onDelete = {
                            viewModel.delete(recado)
                            showUndo("Recado excluído") { viewModel.restore(recado) }
                        }
                        SwipeToDelete(onDelete = onDelete, modifier = Modifier.animateItem()) {
                            RecadoCard(
                                recado = recado,
                                onToggle = { viewModel.toggle(recado.id) },
                                onEdit = { editing = recado },
                                onShare = { share(recado) },
                                onDelete = onDelete,
                            )
                        }
                    }
                }
            }
        }
    }

    editing?.let { current ->
        RecadoSheet(
            initial = current,
            onDismiss = { editing = null },
            onConfirm = { title, note, priority ->
                if (current.id == 0L) {
                    viewModel.add(title, note, priority)
                } else {
                    viewModel.edit(current.id, title, note, priority)
                }
                editing = null
            },
        )
    }
}

@Composable
private fun SwipeToDelete(
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val state = rememberSwipeToDismissBoxState()
    LaunchedEffect(state.currentValue) {
        if (state.currentValue == SwipeToDismissBoxValue.EndToStart) onDelete()
    }
    SwipeToDismissBox(
        state = state,
        enableDismissFromStartToEnd = false,
        modifier = modifier,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PriorityHigh, RoundedCornerShape(20.dp))
                    .padding(end = 24.dp),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Excluir", tint = Color.White)
            }
        },
    ) {
        content()
    }
}
