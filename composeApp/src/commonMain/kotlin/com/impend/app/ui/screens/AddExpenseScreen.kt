package com.impend.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.impend.app.presentation.AddExpenseUiState
import com.impend.app.presentation.AddExpenseViewModel
import com.impend.app.ui.components.AppCard
import com.impend.app.ui.components.CommitmentCheckDialog
import com.impend.app.ui.components.MoodSelector
import com.impend.app.ui.components.ResilienceOverlay
import com.impend.app.ui.theme.ImpendColors
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddExpenseScreen(
    onCompleted: () -> Unit,
    viewModel: AddExpenseViewModel = koinViewModel()
) {
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var mood by remember { mutableStateOf<Int?>(null) }
    
    val uiState by viewModel.uiState.collectAsState()
    val activePledge by viewModel.activePledge.collectAsState()
    
    // Reset state on entry
    LaunchedEffect(Unit) {
        viewModel.resetState()
    }

    // Handle success state for navigation
    LaunchedEffect(uiState) {
        if (uiState is AddExpenseUiState.Success) {
            onCompleted()
        }
    }

    // Main UI
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Log New Expense", style = MaterialTheme.typography.h1)
            Spacer(modifier = Modifier.height(24.dp))

            AppCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text("How much did you spend?", style = MaterialTheme.typography.body2)
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("0.00") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = ImpendColors.Primary,
                            cursorColor = ImpendColors.Primary
                        ),
                        enabled = uiState !is AddExpenseUiState.Processing
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Category", style = MaterialTheme.typography.body2)
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("e.g. Coffee, Groceries") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = ImpendColors.Primary,
                            cursorColor = ImpendColors.Primary
                        ),
                        enabled = uiState !is AddExpenseUiState.Processing
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("How are you feeling?", style = MaterialTheme.typography.body2)
                    MoodSelector(
                        selectedMood = mood,
                        onMoodSelected = { mood = it }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (category.isNotBlank()) {
                        val amt = amount.toDoubleOrNull() ?: 0.0
                        if (amt > 0) {
                            viewModel.checkForPledge(category) {
                                viewModel.validateAndSaveExpense(amt, category, mood)
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(backgroundColor = ImpendColors.Primary),
                enabled = uiState !is AddExpenseUiState.Processing
            ) {
                if (uiState is AddExpenseUiState.Processing) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = ImpendColors.TextPrimary)
                } else {
                    Text("Securely Log Expense", color = ImpendColors.TextPrimary)
                }
            }
        }

        // Overlays
        if (activePledge != null) {
            CommitmentCheckDialog(
                pledge = activePledge!!,
                onConfirmBreak = {
                    viewModel.markPledgeBroken(activePledge!!.id) {
                        val amt = amount.toDoubleOrNull() ?: 0.0
                        viewModel.validateAndSaveExpense(amt, category, mood)
                    }
                },
                onDismiss = {
                    viewModel.dismissPledge()
                }
            )
        }

        if (uiState is AddExpenseUiState.InterceptionRequired) {
            val prompt = (uiState as AddExpenseUiState.InterceptionRequired).prompt
            ResilienceOverlay(
                prompt = prompt,
                onProceed = { viewModel.proceedWithPendingExpense() },
                onCancel = { viewModel.cancelPendingExpense() }
            )
        }
        
        if (uiState is AddExpenseUiState.Error) {
            val message = (uiState as AddExpenseUiState.Error).message
            // simple snackbar or text could go here, for now just log
            println("Error saving expense: $message")
        }
    }
}
