package com.progmobile.lembraplus.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.progmobile.lembraplus.R
import com.progmobile.lembraplus.ui.components.CategoryCard.CategoryCard
import com.progmobile.lembraplus.ui.components.CategoryCard.CategoryCardProps
import com.progmobile.lembraplus.ui.components.NavBar.NavBar
import com.progmobile.lembraplus.ui.components.NavBar.NavProps
import com.progmobile.lembraplus.ui.components.TaskCard
import com.progmobile.lembraplus.ui.components.TaskCardProps

@Composable
fun HomeScreen(navController: NavHostController) {
    val scrollVertical = rememberScrollState()
    val scrollHorizontal = rememberScrollState()

    Scaffold(
        bottomBar = {
            NavBar(props = NavProps(navController, "home"))
        }
    ) { pad ->

        Column (
            modifier = Modifier
                .padding(pad)
                .padding(start = 25.dp, end = 25.dp)
                .verticalScroll(scrollHorizontal)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo App",
                    modifier = Modifier
                        .width(90.dp)
                )
            }

            // Favorited
            Text(
                "Favorited",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .horizontalScroll(scrollVertical)
                    .clip(RoundedCornerShape(10.dp)),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                TaskCard(props = TaskCardProps(
                    title = "Criar Apresentação",
                    description = "Fiquei com o capitulo 1.9, criar uma apresentação sobre a conciencia negra, indigena e outros novos minorias no brasil",
                    categoryName = "trabalho",
                    categoryColorHex = "#FF9800",
                    width = 300,
                    isPinned = true
                ))

                TaskCard(props = TaskCardProps(
                    title = "Criar Apresentação",
                    description = "Fiquei com o capitulo 1.9, criar uma apresentação sobre a conciencia negra, indigena e outros novos minorias no brasil",
                    categoryName = "trabalho",
                    categoryColorHex = "#FF9800",
                    width = 300,
                    isPinned = true
                ))

                TaskCard(props = TaskCardProps(
                    title = "Criar Apresentação",
                    description = "Fiquei com o capitulo 1.9, criar uma apresentação sobre a conciencia negra, indigena e outros novos minorias no brasil",
                    categoryName = "trabalho",
                    categoryColorHex = "#FF9800",
                    width = 300,
                    isPinned = true
                ))
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Categorys
            Text(
                "Categorys",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))
            CategoryCard(props = CategoryCardProps(
                name = "Trabalho",
                colorHex = "#FF9800"
            ))

            Spacer(modifier = Modifier.height(10.dp))
            CategoryCard(props = CategoryCardProps(
                name = "Trabalho",
                colorHex = "#FF9800"
            ))

            Spacer(modifier = Modifier.height(10.dp))
            CategoryCard(props = CategoryCardProps(
                name = "Trabalho",
                colorHex = "#FF9800"
            ))

            Spacer(modifier = Modifier.height(10.dp))

            // lateste tasks added
            Text(
                "latest tasks added",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))

            TaskCard(props = TaskCardProps(
                title = "Criar Apresentação",
                description = "Fiquei com o capitulo 1.9, criar uma apresentação sobre a conciencia negra, indigena e outros novos minorias no brasil",
                categoryName = "trabalho",
                categoryColorHex = "#FF9800",
                isPinned = false
            ))

            Spacer(modifier = Modifier.height(10.dp))

            TaskCard(props = TaskCardProps(
                title = "Criar Apresentação",
                description = "Fiquei com o capitulo 1.9, criar uma apresentação sobre a conciencia negra, indigena e outros novos minorias no brasil",
                categoryName = "trabalho",
                categoryColorHex = "#FF9800",
                isPinned = false
            ))

            Spacer(modifier = Modifier.height(10.dp))

            TaskCard(props = TaskCardProps(
                title = "Criar Apresentação",
                description = "Fiquei com o capitulo 1.9, criar uma apresentação sobre a conciencia negra, indigena e outros novos minorias no brasil",
                categoryName = "trabalho",
                categoryColorHex = "#FF9800",
                isPinned = false
            ))

        }
    }
}

