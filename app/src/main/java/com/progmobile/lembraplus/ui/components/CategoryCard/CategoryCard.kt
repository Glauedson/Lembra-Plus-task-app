package com.progmobile.lembraplus.ui.components.CategoryCard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.progmobile.lembraplus.utils.ColorUtils

@Composable
fun CategoryCard(props: CategoryCardProps) {

    val categoryColor = ColorUtils.safeParseColor(props.colorHex)

    Box (
        modifier = Modifier
            .border(
                width = 1.dp,
                color = categoryColor,
                shape = RoundedCornerShape(15.dp)
            )
            .background(
                categoryColor.copy(alpha = 0.15f),
                shape = RoundedCornerShape(15.dp)
            )
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .fillMaxWidth(),
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = props.name,
                    color = categoryColor
                )
                Text(
                    text = "${props.quant} tasks",
                    color = categoryColor
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Category edit icon",
                    modifier = Modifier.size(23.dp),
                    tint = categoryColor
                )
            }
        }
    }
}