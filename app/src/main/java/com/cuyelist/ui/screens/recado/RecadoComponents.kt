package com.cuyelist.ui.screens.recado

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuyelist.R
import com.cuyelist.model.Priority
import com.cuyelist.model.Recado
import com.cuyelist.model.RecadoFilter
import com.cuyelist.ui.theme.PriorityHigh
import com.cuyelist.ui.theme.PriorityLow
import com.cuyelist.ui.theme.PriorityMedium
import com.cuyelist.ui.theme.RecadoBlue
import com.cuyelist.ui.theme.RecadoInk
import com.cuyelist.ui.theme.RecadoNavy

internal fun Priority.color(): Color = when (this) {
    Priority.LOW -> PriorityLow
    Priority.MEDIUM -> PriorityMedium
    Priority.HIGH -> PriorityHigh
}

@Composable
internal fun RecadosHeader(
    total: Int,
    done: Int,
    canClearDone: Boolean,
    onBack: () -> Unit,
    onClearDone: () -> Unit,
) {
    val pending = total - done
    val progress by animateFloatAsState(
        targetValue = if (total == 0) 0f else done / total.toFloat(),
        label = "progress",
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(listOf(RecadoBlue, RecadoNavy)),
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
            )
            .statusBarsPadding()
            .padding(start = 8.dp, end = 8.dp, bottom = 24.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar", tint = Color.White)
            }
            Text(
                text = "Meus recados",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
            )
            IconButton(enabled = canClearDone, onClick = onClearDone) {
                Icon(
                    Icons.Default.DeleteSweep,
                    contentDescription = "Limpar concluídos",
                    tint = Color.White.copy(alpha = if (canClearDone) 1f else 0.4f),
                )
            }
        }
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = when {
                    total == 0 -> "Nada por aqui ainda"
                    pending == 0 -> "Tudo em dia! 🎉"
                    pending == 1 -> "1 recado pendente"
                    else -> "$pending recados pendentes"
                },
                color = Color.White,
                fontSize = 28.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp),
            )
            Text(
                text = "$done de $total concluídos",
                color = Color.White.copy(alpha = 0.85f),
                modifier = Modifier.padding(top = 2.dp, bottom = 12.dp),
            )
            LinearProgressIndicator(
                progress = { progress },
                color = Color.White,
                trackColor = Color.White.copy(alpha = 0.3f),
                gapSize = 0.dp,
                drawStopIndicator = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape),
            )
        }
    }
}

@Composable
internal fun SearchField(query: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Buscar nos recados") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Close, contentDescription = "Limpar busca")
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(28.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            unfocusedBorderColor = Color(0xFFD5DEF5),
            focusedBorderColor = RecadoBlue,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    )
}

@Composable
internal fun FilterRow(
    selected: RecadoFilter,
    counts: Map<RecadoFilter, Int>,
    onSelect: (RecadoFilter) -> Unit,
) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        RecadoFilter.entries.forEach { option ->
            FilterChip(
                selected = selected == option,
                onClick = { onSelect(option) },
                label = { Text("${option.label} · ${counts[option] ?: 0}") },
            )
        }
    }
}

@Composable
internal fun EmptyState(hasRecados: Boolean, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.cuyes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
        )
        Text(
            text = if (hasRecados) "Nenhum recado encontrado" else "Sua lista está vazia",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = RecadoInk,
            modifier = Modifier.padding(top = 16.dp),
        )
        Text(
            text = if (hasRecados) "Tente outra busca ou filtro." else "Toque em “Novo recado” para começar.",
            color = RecadoInk.copy(alpha = 0.65f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}

@Composable
internal fun RecadoCard(
    recado: Recado,
    onToggle: () -> Unit,
    onEdit: () -> Unit,
    onShare: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val accent = recado.priority.color()
    var menuOpen by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 2.dp,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onEdit),
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight()
                    .background(if (recado.isDone) accent.copy(alpha = 0.35f) else accent),
            )
            IconButton(onClick = onToggle, modifier = Modifier.align(Alignment.CenterVertically)) {
                Icon(
                    imageVector = if (recado.isDone) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                    contentDescription = if (recado.isDone) "Marcar como pendente" else "Marcar como concluído",
                    tint = if (recado.isDone) PriorityLow else RecadoNavy.copy(alpha = 0.6f),
                    modifier = Modifier.size(28.dp),
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp),
            ) {
                Text(
                    text = recado.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = RecadoInk.copy(alpha = if (recado.isDone) 0.5f else 1f),
                    textDecoration = if (recado.isDone) TextDecoration.LineThrough else null,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                if (recado.note.isNotBlank()) {
                    Text(
                        text = recado.note,
                        style = MaterialTheme.typography.bodyMedium,
                        color = RecadoInk.copy(alpha = 0.65f),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 2.dp),
                    )
                }
                Text(
                    text = "Prioridade ${recado.priority.label.lowercase()}",
                    color = accent,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .background(accent.copy(alpha = 0.12f), CircleShape)
                        .padding(horizontal = 10.dp, vertical = 3.dp),
                )
            }
            Box(modifier = Modifier.align(Alignment.Top)) {
                IconButton(onClick = { menuOpen = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Mais opções")
                }
                DropdownMenu(expanded = menuOpen, onDismissRequest = { menuOpen = false }) {
                    DropdownMenuItem(
                        text = { Text("Editar") },
                        leadingIcon = { Icon(Icons.Default.Edit, null) },
                        onClick = { menuOpen = false; onEdit() },
                    )
                    DropdownMenuItem(
                        text = { Text("Compartilhar") },
                        leadingIcon = { Icon(Icons.Default.Share, null) },
                        onClick = { menuOpen = false; onShare() },
                    )
                    DropdownMenuItem(
                        text = { Text("Excluir") },
                        leadingIcon = { Icon(Icons.Default.Delete, null, tint = PriorityHigh) },
                        onClick = { menuOpen = false; onDelete() },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RecadoSheet(
    initial: Recado,
    onDismiss: () -> Unit,
    onConfirm: (title: String, note: String, priority: Priority) -> Unit,
) {
    val isNew = initial.id == 0L
    var title by rememberSaveable { mutableStateOf(initial.title) }
    var note by rememberSaveable { mutableStateOf(initial.note) }
    var priority by rememberSaveable { mutableStateOf(initial.priority) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) { if (isNew) focusRequester.requestFocus() }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = Color.White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp)
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = if (isNew) "Novo recado" else "Editar recado",
                fontSize = 22.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = RecadoInk,
            )
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
            )
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Detalhes (opcional)") },
                minLines = 3,
                maxLines = 5,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Prioridade", fontWeight = FontWeight.SemiBold, color = RecadoInk)
                SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                    Priority.entries.forEachIndexed { index, option ->
                        SegmentedButton(
                            selected = priority == option,
                            onClick = { priority = option },
                            shape = SegmentedButtonDefaults.itemShape(index, Priority.entries.size),
                            label = { Text(option.label) },
                        )
                    }
                }
            }
            Button(
                enabled = title.isNotBlank(),
                onClick = { onConfirm(title, note, priority) },
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
            ) {
                Text(if (isNew) "Adicionar" else "Salvar", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
