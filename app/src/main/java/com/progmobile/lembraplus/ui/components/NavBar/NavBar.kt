package com.progmobile.lembraplus.ui.components.NavBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .offset(y = 40.dp)
                .shadow(
                    elevation = 15.dp,
                    shape = RoundedCornerShape(0.dp),
                    ambientColor = Color.Black,
                )
                .background(color = MaterialTheme.colorScheme.background)
                .padding(start = 45.dp, end = 45.dp)
        ) {
            Spacer(modifier = Modifier.width(60.dp))
            Spacer(modifier = Modifier.width(60.dp))
            Spacer(modifier = Modifier.width(60.dp))
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 45.dp, end = 45.dp, top = 30.dp)
        ) {
            IconButton(
                onClick = { props.navController.navigate("Home") },
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
                    .offset(y = 10.dp)
            ) {
                Icon(
                    imageVector = homeIconState.icon,
                    contentDescription = "Home",
                    tint = homeIconState.tint,
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
                )
            }

            IconButton(
                onClick = { props.navController.navigate("createNote") },
                modifier = Modifier
                    .offset(y = (-33).dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(50.dp)
                    )
                    .width(70.dp)
                    .height(90.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create Note",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                )
            }

            IconButton(
                onClick = { props.navController.navigate("about") },
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
                    .offset(y = 10.dp)
            ) {
                Icon(
                    imageVector = aboutIconState.icon,
                    contentDescription = "About",
                    tint = aboutIconState.tint,
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
                )
            }
        }
    }
}