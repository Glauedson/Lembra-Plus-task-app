package com.progmobile.lembraplus.ui.components.NewCategoryModal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.progmobile.lembraplus.data.db.model.Category
import com.progmobile.lembraplus.ui.vms.CategoryViewModel
import com.progmobile.lembraplus.utils.ColorUtils

@Composable
fun NewCategoryModal(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel,
    onCreated: (Category) -> Unit = {}
) {

    var showDialog by rememberSaveable { mutableStateOf(true) }

    Button(
        onClick = { showDialog = true }, colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = Color.White,
        ), shape = RoundedCornerShape(30.dp)
    ) {
        Text("+ New Category")
    }

    if (showDialog) {
        CreateCategoryDialog(
            onDismiss = { showDialog = false },
            onConfirm = { name, selectedColor ->

                val colorHex = ColorUtils.colorToHex(selectedColor)
                viewModel.saveCategory(name, colorHex, id = 0)

                showDialog = false
            }
        )
    }
}

@Composable
private fun CreateCategoryDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, selectedColor: Color) -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val availableColors = listOf(
        Color(0xFFE45D49),
        Color(0xFFFD9637),
        Color(0xFF97CD1F),
        Color(0xFF4BB7C4),
        Color(0xFF3DA1FF),
        Color(0xFFB956E5),
        Color(0xFF6B55DA)
    )

    var selectedColor by remember { mutableStateOf<Color?>(null) }
    val focusManager = LocalFocusManager.current

    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "New Category",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        if (it.isNotBlank()) error = null
                    },
                    placeholder = {
                        Text(
                            if (error == null) "Family & Friends" else "$error",
                            color = if (error == null) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    shape = RoundedCornerShape(20.dp),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    isError = error != null
                )
                Text(
                    "Select a color",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                ColorSelector(
                    colors = availableColors,
                    selectedColor = selectedColor,
                    onColorSelected = { selectedColor = it }
                )
            }
        }, confirmButton = {
            OutlinedButton(
                onClick = {
                    val trimmed = name.trim()
                    if (trimmed.isEmpty()) {
                        error = "Inform a name!"
                        return@OutlinedButton
                    }

                    val colorHex = selectedColor ?: Color(0xFF9D9D9D)

                    onConfirm(trimmed, colorHex)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(0.dp, Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    "Add Category +", style = MaterialTheme.typography.titleSmall
                )
            }
        }, shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun ColorSelector(
    colors: List<Color>, selectedColor: Color?, onColorSelected: (Color) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        colors.forEach { color ->
            Box(
                modifier = Modifier
                    .zIndex(if (color == selectedColor) 1f else 0f)
                    .graphicsLayer {
                        if (color == selectedColor) translationY = -20f else 1f
                    }
                    .dropShadow(
                        shape = RoundedCornerShape(50),
                        shadow = if (color == selectedColor) Shadow(
                            radius = 10.dp,
                            color.copy(0.60f),
                            spread = 1.dp,
                            offset = DpOffset(y = 6.dp, x = 0.dp)
                        ) else Shadow(
                            radius = 0.dp
                        )
                    )
                    .clip(RoundedCornerShape(50))
                    .size(40.dp)
                    .background(color)
                    .clickable { onColorSelected(color) }
            )
        }
    }
}
