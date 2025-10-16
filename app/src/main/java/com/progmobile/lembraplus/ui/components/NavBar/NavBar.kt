package com.progmobile.lembraplus.ui.components.NavBar

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NavBar(
    props: NavProps
) {
    val (homeIconState, aboutIconState) = createNavBarProps(
        currentScreen = props.currentScreen,
        navController = props.navController
    )

    val lineOffset by animateDpAsState(
        targetValue = when (props.currentScreen.lowercase()) {
            "home" -> 45.dp
            "about" -> 279.dp
            else -> 45.dp
        },
        animationSpec = tween(durationMillis = 300),
        label = "line_animation"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .shadow(
                    elevation = 15.dp,
                    shape = RoundedCornerShape(0.dp),
                    ambientColor = Color.Black,
                )
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Box(
                modifier = Modifier
                    .offset(x = lineOffset)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(1.dp)
                    )
                    .width(60.dp)
                    .height(3.dp)
                    .shadow(
                        elevation = 2.dp,
                        spotColor = MaterialTheme.colorScheme.primary,
                        ambientColor = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(1.dp)
                    )
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 45.dp, end = 45.dp)
        ) {
            IconButton(
                onClick = { props.navController.navigate("Home") },
                modifier = Modifier
                    .size(60.dp)
                    .offset(y = 10.dp)
            ) {
                Icon(
                    imageVector = homeIconState.icon,
                    contentDescription = "Home",
                    tint = homeIconState.tint,
                    modifier = Modifier
                        .size(35.dp)
                )
            }

            IconButton(
                onClick = { props.navController.navigate("createNote") },
                modifier = Modifier
                    .offset(y = (-23).dp)
                    .size(61.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(50)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create Note",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(30.dp)
                )
            }

            IconButton(
                onClick = { props.navController.navigate("about") },
                modifier = Modifier
                    .size(60.dp)
                    .offset(y = 10.dp)
            ) {
                Icon(
                    imageVector = aboutIconState.icon,
                    contentDescription = "About",
                    tint = aboutIconState.tint,
                    modifier = Modifier
                        .size(35.dp)
                )
            }
        }
    }
}