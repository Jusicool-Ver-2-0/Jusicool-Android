package com.jusicool.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jusicool.signup.view.SignUpRoute

const val signUpRoute = "signUpRoute"

fun NavController.navigateToSignUpRoute(napOptions: NavOptions? = null) {
    this.navigate(signUpRoute, napOptions)
}

fun NavGraphBuilder.signUpRoute(
    navigateToSignIn: () -> Unit,
) {
    composable(signUpRoute) {
        SignUpRoute(
            navigateToSignIn = navigateToSignIn
        )
    }
}