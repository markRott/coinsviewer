package com.rt.coinsviewer.common

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp

@Composable
fun BottomAppBar() {
    val selectedIndex = remember { mutableStateOf(0) }

    BottomNavigation(elevation = 10.dp) {

        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Home, "") },
            label = { Text(text = "Home") },
            selected = (selectedIndex.value == 0),
            onClick = { selectedIndex.value = 0 }
        )

        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Favorite, "") },
            label = { Text(text = "Favorite") },
            selected = (selectedIndex.value == 1),
            onClick = { selectedIndex.value = 1 }
        )

//        BottomNavigationItem(
//            icon = { Icon(imageVector = Icons.Default.Person, "") },
//            label = { Text(text = "Profile") },
//            selected = (selectedIndex.value == 2),
//            onClick = { selectedIndex.value = 2 })
    }
}