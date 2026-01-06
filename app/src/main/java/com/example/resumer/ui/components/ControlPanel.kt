package com.example.resumer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog

@Composable
fun ControlPanel(
    fontSize: Float,
    onFontSizeChange: (Float) -> Unit,
    currentFontColor: Color,
    onFontColorChange: (Color) -> Unit,
    currentBgColor: Color,
    onBgColorChange: (Color) -> Unit,
    modifier: Modifier = Modifier
) {

    var showFontColorDialog by remember { mutableStateOf(false) }
    var showBgColorDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        shadowElevation = 16.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            //font size slider
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "FONT SIZE",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${fontSize.toInt()} sp",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Slider(
                value = fontSize,
                onValueChange = onFontSizeChange,
                valueRange = 12f..32f,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            //color buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Font Color Button
                ColorSelectorButton(
                    label = "Text Color",
                    currentColor = currentFontColor,
                    onClick = { showFontColorDialog = true },
                    modifier = Modifier.weight(1f)
                )

                //background color button
                ColorSelectorButton(
                    label = "Page Color",
                    currentColor = currentBgColor,
                    onClick = { showBgColorDialog = true },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }

    //dialog box
    if (showFontColorDialog) {
        ColorPickerDialog(
            title = "Select Text Color",
            currentColor = currentFontColor,
            onColorSelected = {
                onFontColorChange(it)
                showFontColorDialog = false
            },
            onDismiss = { showFontColorDialog = false }
        )
    }

    if (showBgColorDialog) {
        ColorPickerDialog(
            title = "Select Page Color",
            currentColor = currentBgColor,
            onColorSelected = {
                onBgColorChange(it)
                showBgColorDialog = false
            },
            onDismiss = { showBgColorDialog = false }
        )
    }
}

@Composable
fun ColorSelectorButton(
    label: String,
    currentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(50.dp),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(currentColor)
                    .border(1.dp, Color.Gray.copy(alpha = 0.5f), CircleShape)
            )
        }
    }
}

@Composable
fun ColorPickerDialog(
    title: String,
    currentColor: Color,
    onColorSelected: (Color) -> Unit,
    onDismiss: () -> Unit
) {

    val colors = listOf(
        Color.Black, Color.DarkGray, Color.Gray, Color.LightGray, Color.White,
        Color(0xFFD32F2F), Color(0xFFC2185B), Color(0xFF7B1FA2), Color(0xFF512DA8), // Reds/Purples
        Color(0xFF303F9F), Color(0xFF1976D2), Color(0xFF0288D1), Color(0xFF0097A7), // Blues
        Color(0xFF00796B), Color(0xFF388E3C), Color(0xFF689F38), Color(0xFFAFB42B), // Greens
        Color(0xFFFBC02D), Color(0xFFFFA000), Color(0xFFF57C00), Color(0xFFE64A19), // Yellows/Oranges
        Color(0xFF5D4037), Color(0xFF616161), Color(0xFF455A64) // Earth Tones
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.heightIn(max = 400.dp)
                ) {
                    items(colors) { color ->
                        ThreeDColorGridItem(
                            color = color,
                            isSelected = color == currentColor,
                            onClick = { onColorSelected(color) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("CANCEL")
                }
            }
        }
    }
}

@Composable
fun ThreeDColorGridItem(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val shape = RoundedCornerShape(10.dp)
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            color.copy(alpha = 0.5f).compositeOver(Color.White),
            color,
            color.copy(alpha = 0.8f).compositeOver(Color.Black)
        ),
        start = Offset.Zero,
        end = Offset.Infinite
    )

    Box(
        modifier = Modifier
            .size(40.dp)
            .shadow(
                elevation = if (isSelected) 2.dp else 6.dp,
                shape = shape
            )
            .clip(shape)
            .background(brush = gradientBrush)
            .then(
                if (isSelected) Modifier.border(3.dp, Color.White, shape) else Modifier
            )
            .clickable { onClick() }
    ) {
        if (isSelected) {
            val iconColor = if (color.luminance() > 0.5f) Color.Black else Color.White
        }
    }
}