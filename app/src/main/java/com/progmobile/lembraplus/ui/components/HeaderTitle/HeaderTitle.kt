package com.progmobile.lembraplus.ui.components.HeaderTitle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class HeaderTitleProps(
    val title: String,
    val onBackClick: () -> Unit = {},
    val maxTitleLength: Int = 20
) {

    val validatedTitle: String = if (title.length > maxTitleLength) {
        "${title.take(maxTitleLength - 3)}..."
    } else {
        title
    }
}

@Composable
fun HeaderTitle(
    props: HeaderTitleProps
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp)
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        // Back
        IconButton(
            onClick = props.onBackClick,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(10.dp))
                .width(45.dp)
                .height(45.dp)
                .align(Alignment.CenterStart),
        ) {

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        // Title
        Text(
            text = props.validatedTitle,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 70.dp),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun HeaderTitle(
    title: String,
    navController: NavController,
    maxTitleLength: Int = 20
) {
    val props = HeaderTitleProps(
        title = title,
        onBackClick = {
            navController.navigate("home") {
                popUpTo("home") { inclusive = false }
            }
        },
        maxTitleLength = maxTitleLength
    )

    HeaderTitle(props = props)
}

@Composable
fun HeaderTitle(
    title: String,
    onBackClick: () -> Unit,
    maxTitleLength: Int = 20
) {
    val props = HeaderTitleProps(
        title = title,
        onBackClick = onBackClick,
        maxTitleLength = maxTitleLength
    )

    HeaderTitle(props = props)
}

@Composable
@Preview(showBackground = true)
fun PreviewHeaderTitle() {
    HeaderTitle(
        props = HeaderTitleProps(
            title = "About",
            onBackClick = {}
        )
    )
}
