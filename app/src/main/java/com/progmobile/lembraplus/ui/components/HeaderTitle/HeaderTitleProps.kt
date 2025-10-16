package com.progmobile.lembraplus.ui.components.HeaderTitle

data class HeaderTitleProps(
    val title: String,
    val backButton: () -> Unit = {},
    val deleteButton: () -> Unit = {},
) {
    val validatedTitle: String = if (title.length > 20) {
        "${title.take(17)}..."
    } else {
        title
    }
}