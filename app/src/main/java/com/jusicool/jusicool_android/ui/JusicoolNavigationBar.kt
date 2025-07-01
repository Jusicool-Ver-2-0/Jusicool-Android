package com.jusicool.jusicool_android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.theme.JusicoolTheme

@Composable
fun JusicoolNavigationBar(modifier: Modifier = Modifier, appState: JusicoolAppState) {
    JusicoolTheme { colors, typography ->

        val items = appState.tabs
        val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Row(
            modifier = modifier
                .height(52.dp)
                .fillMaxWidth()
                .background(colors.white)
                .padding(vertical = 8.dp, horizontal = 41.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            items.forEach { screen ->
                val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                val contentColor = if (selected) colors.main else colors.gray400

                Column(
                    modifier = Modifier
                        .JusicoolClickable {
                            if (currentDestination?.route != screen.route) {
                                appState.navController.navigate(screen.route) {
                                    popUpTo(appState.navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(screen.icon),
                        contentDescription = screen.label,
                        tint = contentColor,
                        modifier = Modifier.size(24.dp)
                    )

                    Text(
                        text = screen.label,
                        color = contentColor,
                        style = typography.navi
                    )
                }
            }
        }
    }
}