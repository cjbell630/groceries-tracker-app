package com.example.groceriestracker

import android.graphics.drawable.Drawable
import android.media.Image
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.iconics.compose.Image

interface FriendlyIcon {
    @Composable
    fun Display(modifier:Modifier): Unit
}

class FriendlyIconicsIcon(private val icon: IIcon, private val color: Color) : FriendlyIcon {
    @Composable
    override fun Display(modifier: Modifier) {
        Image(icon, colorFilter =ColorFilter.tint(color=color), modifier = modifier)
    }
}

class FriendlyMaterialIcon(private val icon: ImageVector, private val color:Color) : FriendlyIcon {
    @Composable
    override fun Display(modifier: Modifier) {
        Icon(
            icon,
            contentDescription = "icon",
            tint = color,
            modifier = modifier
        )
    }
}