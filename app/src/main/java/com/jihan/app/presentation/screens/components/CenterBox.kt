package com.jihan.app.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier


@Composable
fun CenterBox(
    modifier: Modifier = Modifier.fillMaxSize(),
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally
    ) { content() }
}