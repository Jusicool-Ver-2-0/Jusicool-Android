package com.jusicool.signin.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jusicool.signin.view.SignInRoute

const val signInRoute = "signInRoute"

fun NavController.navigateToSignInRoute(napOptions: NavOptions? = null) {
    this.navigate(signInRoute, napOptions)
}

fun NavGraphBuilder.signInRoute(
    navigateToSignUp: () -> Unit,
    navigateToAccount: () -> Unit
) {
    composable(signInRoute) {
        SignInRoute(
            navigateToSignUp = navigateToSignUp,
            navigateToAccount = navigateToAccount
        )
    }
}