package dev.pkos98.clanki.utils

import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

public fun NavDirections.navigate(@IdRes currentDest: Int, view: View) {
    val navController = Navigation.findNavController(view)
    if (navController.currentDestination?.id == currentDest)
        navController.navigate(this)
}