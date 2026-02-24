package com.impend.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.impend.app.presentation.HomeUiState
import com.impend.app.presentation.HomeViewModel
import com.impend.app.ui.screens.AddExpenseScreen
import com.impend.app.ui.screens.DashboardScreen
import com.impend.app.ui.screens.PledgeManagementScreen
import com.impend.app.ui.screens.ProgressScreen
import com.impend.app.ui.screens.CirclesScreen
import com.impend.app.ui.screens.SettingsScreen
import com.impend.app.ui.theme.ImpendColors
import com.impend.app.ui.theme.ImpendTheme
import org.jetbrains.compose.resources.stringResource
import impend.composeapp.generated.resources.Res
import impend.composeapp.generated.resources.*
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    ImpendTheme {
        KoinContext {
            val viewModel: HomeViewModel = koinViewModel()
            val state by viewModel.uiState.collectAsState()
            var currentScreen by remember { mutableStateOf<Screen>(Screen.Dashboard) }

            Scaffold(
                bottomBar = {
                    BottomNavigation(
                        backgroundColor = ImpendColors.Surface,
                        contentColor = ImpendColors.Primary,
                        elevation = 16.dp
                    ) {
                        ModernBottomNavItem(
                            icon = "📊",
                            label = stringResource(Res.string.nav_home),
                            selected = currentScreen is Screen.Dashboard,
                            onClick = { currentScreen = Screen.Dashboard }
                        )
                        ModernBottomNavItem(
                            icon = "📈",
                            label = stringResource(Res.string.nav_trends),
                            selected = currentScreen is Screen.Progress,
                            onClick = { currentScreen = Screen.Progress }
                        )
                        ModernBottomNavItem(
                            icon = "➕",
                            label = stringResource(Res.string.nav_add),
                            selected = currentScreen is Screen.AddExpense,
                            onClick = { currentScreen = Screen.AddExpense }
                        )
                        ModernBottomNavItem(
                            icon = "🫂",
                            label = stringResource(Res.string.nav_circles),
                            selected = currentScreen is Screen.Circles,
                            onClick = { currentScreen = Screen.Circles }
                        )
                        ModernBottomNavItem(
                            icon = "⚙️",
                            label = stringResource(Res.string.nav_settings),
                            selected = currentScreen is Screen.Settings,
                            onClick = { currentScreen = Screen.Settings }
                        )
                    }
                }
            ) { paddingValues ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    when (currentScreen) {
                        Screen.Dashboard -> {
                            when (val s = state) {
                                is HomeUiState.Success -> {
                                    DashboardScreen(
                                        state = s,
                                        onAddClick = { currentScreen = Screen.AddExpense },
                                        onPledgeClick = { currentScreen = Screen.PledgeManagement },
                                        onProgressClick = { /* Handled by Bottom Nav */ },
                                        onCirclesClick = { /* Handled by Bottom Nav */ },
                                        onDeleteClick = { id -> viewModel.onDeleteExpenseClicked(id) }
                                    )
                                }
                                is HomeUiState.Loading -> {
                                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                        CircularProgressIndicator(color = ImpendColors.Primary)
                                    }
                                }
                                is HomeUiState.Error -> {
                                    Text("Error: ${s.message}")
                                }
                            }
                        }
                        Screen.AddExpense -> {
                            AddExpenseScreen(
                                onCompleted = {
                                    currentScreen = Screen.Dashboard
                                }
                            )
                        }
                        Screen.PledgeManagement -> {
                            PledgeManagementScreen(
                                onBack = { currentScreen = Screen.Dashboard }
                            )
                        }
                        Screen.Progress -> {
                            ProgressScreen(
                                onBack = { currentScreen = Screen.Dashboard }
                            )
                        }
                        Screen.Circles -> {
                            CirclesScreen(
                                onBack = { currentScreen = Screen.Dashboard }
                            )
                        }
                        Screen.Settings -> {
                            SettingsScreen(
                                onBack = { currentScreen = Screen.Dashboard }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.ModernBottomNavItem(
    icon: String,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    BottomNavigationItem(
        icon = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = icon,
                    fontSize = if (selected) 22.sp else 18.sp,
                    color = if (selected) ImpendColors.Primary else ImpendColors.TextSecondary
                )
                if (selected) {
                    Box(
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .size(4.dp)
                            .background(ImpendColors.Primary, shape = androidx.compose.foundation.shape.CircleShape)
                    )
                }
            }
        },
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.caption,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                color = if (selected) ImpendColors.Primary else ImpendColors.TextSecondary
            )
        },
        selected = selected,
        onClick = onClick,
        alwaysShowLabel = true
    )
}

sealed class Screen {
    object Dashboard : Screen()
    object AddExpense : Screen()
    object PledgeManagement : Screen()
    object Progress : Screen()
    object Circles : Screen()
    object Settings : Screen()
}
