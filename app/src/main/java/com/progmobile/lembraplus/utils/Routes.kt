package com.progmobile.lembraplus.utils

sealed class Routes(val route: String) {
    object Home : Routes("Home")
    object About : Routes("about")
    object CreateNote : Routes("createNote")
}