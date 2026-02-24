package com.impend.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.impend.app.presentation.PledgeViewModel
import com.impend.app.ui.components.AppCard
import com.impend.app.ui.theme.ImpendColors
import com.impend.shared.domain.model.PledgeStatus
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PledgeManagementScreen(
    onBack: () -> Unit,
    viewModel: PledgeViewModel = koinViewModel()
) {
    val pledges by viewModel.pledges.collectAsState()
    var category by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("24") } // Default 24 hours

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text("Spending Pledges", style = MaterialTheme.typography.h1)
            TextButton(onClick = onBack) { Text("Back") }
        }
        
        Spacer(modifier = Modifier.height(24.dp))

        AppCard(modifier = Modifier.fillMaxWidth()) {
            Column {
                Text("New Behavioral Commitment", style = MaterialTheme.typography.h2)
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category (e.g. Coffee)") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = duration,
                    onValueChange = { duration = it },
                    label = { Text("Duration (Hours)") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = {
                        val hours = duration.toIntOrNull() ?: 24
                        if (category.isNotBlank()) {
                            viewModel.createPledge(category, hours)
                            category = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = ImpendColors.Primary)
                ) {
                    Text("Seal the Pledge", color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Active Commitments", style = MaterialTheme.typography.h2)
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(pledges) { pledge ->
                AppCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(pledge.category, style = MaterialTheme.typography.body1)
                            Text("Status: ${pledge.status}", style = MaterialTheme.typography.caption)
                        }
                        val color = when(pledge.status) {
                            PledgeStatus.ACTIVE -> ImpendColors.Primary
                            PledgeStatus.BROKEN -> ImpendColors.Error
                            PledgeStatus.COMPLETED -> ImpendColors.Success
                        }
                        Text(pledge.status.name, color = color, style = MaterialTheme.typography.h2)
                    }
                }
            }
        }
    }
}
