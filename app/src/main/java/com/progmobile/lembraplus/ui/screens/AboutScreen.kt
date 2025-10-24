package com.progmobile.lembraplus.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.progmobile.lembraplus.ui.components.NavBar.NavBar
import com.progmobile.lembraplus.ui.components.NavBar.NavProps
import com.progmobile.lembraplus.utils.Routes

@Composable
fun AboutScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            NavBar(props = NavProps(navController, "about"))
        }
    ) { pad ->
        Column (
            modifier = Modifier
                .padding(pad)
                .padding(start = 25.dp, end = 25.dp, top = 15.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ){
            HeaderTitle(
                props = HeaderTitleProps(
                    title = stringResource(id = R.string.about_title),
                    backButton = {
                        navController.navigate(Routes.Home.route)
                    }
                )
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                item {
                    val annotatedString = buildAnnotatedString {

                        val baseString = stringResource(id = R.string.about_description_1)
                        val placeholder = "%1\$s"

                        val startIndex = baseString.indexOf(placeholder)

                        if (startIndex != -1) {
                            append(baseString.substring(0, startIndex))
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("Lembra+")
                            }
                            append(baseString.substring(startIndex + placeholder.length))
                        } else {
                            append(baseString)
                        }

                    }
                    Text(text = annotatedString)
                }
                item {
                    val annotatedString = buildAnnotatedString {

                        val baseString = stringResource(id = R.string.about_description_2)
                        val placeholder = "%1\$s"

                        val startIndex = baseString.indexOf(placeholder)

                        if (startIndex != -1) {
                            append(baseString.substring(0, startIndex))
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("Lembra+")
                            }
                            append(baseString.substring(startIndex + placeholder.length))
                        } else {
                            append(baseString)
                        }

                    }
                    Text(text = annotatedString)
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 35.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Text(
                            stringResource(id = R.string.created_by),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(
                                    elevation = 2.dp,
                                    shape = RoundedCornerShape(12.dp),
                                )
                                .background(
                                    color = MaterialTheme.colorScheme.tertiary,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.profile_edson),
                                contentDescription = "Profile",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(90.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            Column(
                                modifier = Modifier
                                    .padding(start = 15.dp)
                            ) {
                                Column (
                                    verticalArrangement = Arrangement.spacedBy((-7).dp)
                                ){
                                    Text(
                                        text = stringResource(id = R.string.creator_name_label),
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "Glauedson Carlos",
                                        fontWeight = FontWeight.Medium,
                                    )
                                }
                                Column (
                                    verticalArrangement = Arrangement.spacedBy((-7).dp)
                                ){
                                    Text(
                                        text = stringResource(id = R.string.creator_github_label),
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "Glauedson",
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(
                                    elevation = 2.dp,
                                    shape = RoundedCornerShape(12.dp),
                                )
                                .background(
                                    color = MaterialTheme.colorScheme.tertiary,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.profile_gustavo),
                                contentDescription = "Profile",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(90.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            Column(
                                modifier = Modifier
                                    .padding(start = 15.dp)
                            ) {
                                Column (
                                    verticalArrangement = Arrangement.spacedBy((-7).dp)
                                ){
                                    Text(
                                        text = stringResource(id = R.string.creator_name_label),
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "Gustavo Sousa",
                                        fontWeight = FontWeight.Medium,
                                    )
                                }
                                Column (
                                    verticalArrangement = Arrangement.spacedBy((-7).dp)
                                ){
                                    Text(
                                        text = stringResource(id = R.string.creator_github_label),
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "GustavoDeltta",
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
