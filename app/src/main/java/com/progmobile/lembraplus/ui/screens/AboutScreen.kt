package com.progmobile.lembraplus.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.progmobile.lembraplus.R
import com.progmobile.lembraplus.ui.components.HeaderTitle.HeaderTitle
import com.progmobile.lembraplus.ui.components.HeaderTitle.HeaderTitleProps
import com.progmobile.lembraplus.utils.Routes

@Composable
fun AboutScreen(navController: NavHostController) {
    Column (
        modifier = Modifier
            .padding(top = 20.dp, start = 25.dp, end = 25.dp)
            .fillMaxSize(),
    ) {
        HeaderTitle(
            props = HeaderTitleProps(
                title = "About",
                onClick = {
                    navController.navigate(Routes.Home.route)
                }
            )
        )

        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = buildAnnotatedString {
                append("Manage your tasks and notes with ease. With ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)) {
                    append("Lembra+")
                }
                append(", you can write down, categorize, and schedule your reminders, ensuring your ideas and commitments are always organized.")
            }
        )

        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)) {
                    append("Lembra+")
                }
                append(" is an academic project developed for the Mobile Development course at the Federal Institute of Jaguaruana (IFCE).")
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            "Created by:",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(12.dp),
                )
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(10.dp)
                ),
        ) {
            Box(
                modifier = Modifier
                    .width(105.dp)
                    .height(105.dp)
                    .padding(10.dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.profile_edson),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp))
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                    .height(90.dp)
            ) {
                Text(
                    text = "Name:",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "Glauedson Carlos",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.offset(y = (-7).dp)
                )
                Text(
                    text = "Github:",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.offset(y = (-7).dp)
                )
                Text(
                    text = "Glauedson",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.offset(y = (-12).dp)
                )
            }
        }


        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(12.dp),
                )
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(10.dp)
                ),
        ) {
            Box(
                modifier = Modifier
                    .width(105.dp)
                    .height(105.dp)
                    .padding(10.dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.profile_gustavo),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp))
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                    .height(90.dp)
            ) {
                Text(
                    text = "Name:",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "Gustavo Sousa",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.offset(y = (-7).dp)
                )
                Text(
                    text = "Github:",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.offset(y = (-7).dp)
                )
                Text(
                    text = "Gustavo Sousa",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.offset(y = (-12).dp)
                )
            }
        }
    }

}
