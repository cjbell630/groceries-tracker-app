package com.example.groceriestracker.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


@Composable
fun GroceriesTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    useDynamic: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme =
        if (useDynamic && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        } else {
            if (darkTheme) DarkColorScheme else LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        //shapes = JetnewsShapes,
        typography = GroceriesTrackerTypography,
        content = content
    )
}
