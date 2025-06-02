package com.jusicool.jusicool_android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jusicool.signin.navigation.signInRoute

@Composable
fun JusicoolNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = signInRoute
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        signInRoute(
            navigateToSignUp = { /*TODO()*/ },
            navigateToAccount = { /*TODO()*/ }
        )

    }
}