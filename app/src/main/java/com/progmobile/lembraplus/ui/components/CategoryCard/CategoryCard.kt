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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavHostController
import com.progmobile.lembraplus.ui.components.NewCategoryModal.NewCategoryModal
import com.progmobile.lembraplus.ui.vms.CategoryViewModel
import com.progmobile.lembraplus.utils.ColorUtils

@Composable
fun CategoryCard(
    props: CategoryCardProps,
    viewModel: CategoryViewModel,
    navController: NavHostController,
) {

    val categoryColor = ColorUtils.safeParseColor(props.colorHex)
    var editModal by rememberSaveable { mutableStateOf(false) }

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
                    text = "${props.quant} notes",
                    color = categoryColor
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                NewCategoryModal(
                    modifier = Modifier,
                    viewModel = viewModel,
                    categoryId = props.id,
                    navController = navController,
                    categoryColor = props.colorHex
                )
            }
        }
    }
    if (editModal){
        NewCategoryModal(
            modifier = Modifier,
            viewModel = viewModel,
            categoryId = props.id,
            navController = navController
        )
    }
}