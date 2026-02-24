package com.impend.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.impend.app.ui.components.AppCard
import com.impend.app.ui.theme.ImpendColors
import com.impend.shared.util.AnonymousIdProvider
import com.impend.shared.domain.repository.PreferencesRepository
import org.jetbrains.compose.resources.stringResource
import impend.composeapp.generated.resources.Res
import impend.composeapp.generated.resources.*
import org.koin.compose.koinInject

@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    val idProvider = koinInject<AnonymousIdProvider>()
    val preferences = koinInject<PreferencesRepository>()
    
    val anonymousId = remember { idProvider.getAnonymousId() }
    val currentCurrency by preferences.currencyCode.collectAsState()
    val currentLanguage by preferences.languageCode.collectAsState()
    
    var showWipeDialog by remember { mutableStateOf(false) }
    var showCurrencyDropdown by remember { mutableStateOf(false) }
    var showLanguageDropdown by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(Res.string.settings_title), style = MaterialTheme.typography.h1)
            TextButton(onClick = onBack) {
                Text(stringResource(Res.string.settings_done), color = ImpendColors.Primary)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Profile Section
        Text(stringResource(Res.string.settings_section_account), style = MaterialTheme.typography.h2)
        Spacer(modifier = Modifier.height(12.dp))
        AppCard(modifier = Modifier.fillMaxWidth()) {
            Column {
                Text(stringResource(Res.string.settings_anonymous_id), style = MaterialTheme.typography.caption)
                Text(anonymousId, style = MaterialTheme.typography.body1)
                Spacer(modifier = Modifier.height(8.dp))
                Text(stringResource(Res.string.settings_identity_desc), style = MaterialTheme.typography.caption)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Preferences
        Text(stringResource(Res.string.settings_section_preferences), style = MaterialTheme.typography.h2)
        Spacer(modifier = Modifier.height(12.dp))
        AppCard(modifier = Modifier.fillMaxWidth()) {
            Column {
                // Language Selector
                Box {
                    PreferenceRow(
                        label = stringResource(Res.string.settings_language), 
                        value = if (currentLanguage == "es") "Español" else "English",
                        onClick = { showLanguageDropdown = true }
                    )
                    DropdownMenu(
                        expanded = showLanguageDropdown,
                        onDismissRequest = { showLanguageDropdown = false }
                    ) {
                        DropdownMenuItem(onClick = { preferences.setLanguageCode("en"); showLanguageDropdown = false }) {
                            Text("English")
                        }
                        DropdownMenuItem(onClick = { preferences.setLanguageCode("es"); showLanguageDropdown = false }) {
                            Text("Español")
                        }
                    }
                }
                Divider(color = ImpendColors.GlassBorder, modifier = Modifier.padding(vertical = 12.dp))
                
                // Currency Selector
                Box {
                    PreferenceRow(
                        label = stringResource(Res.string.settings_currency), 
                        value = currentCurrency,
                        onClick = { showCurrencyDropdown = true }
                    )
                    DropdownMenu(
                        expanded = showCurrencyDropdown,
                        onDismissRequest = { showCurrencyDropdown = false }
                    ) {
                        listOf("USD", "EUR", "GBP", "JPY", "INR").forEach { code ->
                            DropdownMenuItem(onClick = { preferences.setCurrencyCode(code); showCurrencyDropdown = false }) {
                                Text(code)
                            }
                        }
                    }
                }
                
                Divider(color = ImpendColors.GlassBorder, modifier = Modifier.padding(vertical = 12.dp))
                PreferenceRow(label = stringResource(Res.string.settings_notifications), value = stringResource(Res.string.settings_notif_val))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Danger Zone
        Text(stringResource(Res.string.settings_section_security), style = MaterialTheme.typography.h2, color = ImpendColors.Error)
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { showWipeDialog = true },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = ImpendColors.Error.copy(alpha = 0.1f)),
            elevation = ButtonDefaults.elevation(0.dp)
        ) {
            Text(stringResource(Res.string.settings_clear_data), color = ImpendColors.Error)
        }

        Spacer(modifier = Modifier.height(64.dp))
    }

    if (showWipeDialog) {
        AlertDialog(
            onDismissRequest = { showWipeDialog = false },
            title = { Text(stringResource(Res.string.settings_dialog_wipe_title)) },
            text = { Text(stringResource(Res.string.settings_dialog_wipe_desc)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        // In a real app, call a usecase to wipe DB
                        showWipeDialog = false
                    }
                ) {
                    Text(stringResource(Res.string.settings_dialog_wipe_confirm), color = ImpendColors.Error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showWipeDialog = false }) {
                    Text(stringResource(Res.string.settings_dialog_cancel))
                }
            }
        )
    }
}

@Composable
fun PreferenceRow(label: String, value: String, onClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .padding(vertical = 4.dp), // Added padding since clickable absorbs clicks best with padding
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.body1)
        Text(value, style = MaterialTheme.typography.body2, color = ImpendColors.Primary)
    }
}
