package com.impend.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.impend.app.presentation.CirclesViewModel
import com.impend.app.ui.components.AppCard
import com.impend.app.ui.theme.ImpendColors
import com.impend.shared.domain.model.Circle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CirclesScreen(
    onBack: () -> Unit,
    viewModel: CirclesViewModel = koinViewModel()
) {
    val allCircles by viewModel.allCircles.collectAsState()
    val joinedCircles by viewModel.joinedCircles.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Resilience Circles", style = MaterialTheme.typography.h1)
            TextButton(onClick = onBack) {
                Text("Back", color = ImpendColors.Primary)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Your Active Cohorts", style = MaterialTheme.typography.h2)
        Spacer(modifier = Modifier.height(12.dp))

        if (joinedCircles.isEmpty()) {
            Text("You haven't joined any circles yet. Strength in numbers awaits!", style = MaterialTheme.typography.caption)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.weight(0.4f)) {
                items(joinedCircles) { circle ->
                    CircleItem(circle, isJoined = true, onAction = { viewModel.leaveCircle(circle.id) })
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Discover Anonymous Cohorts", style = MaterialTheme.typography.h2)
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.weight(0.6f)) {
            items(allCircles.filter { !it.isJoined }) { circle ->
                CircleItem(circle, isJoined = false, onAction = { viewModel.joinCircle(circle.id) })
            }
        }
    }
}

@Composable
fun CircleItem(
    circle: Circle,
    isJoined: Boolean,
    onAction: () -> Unit
) {
    AppCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(circle.name, style = MaterialTheme.typography.body1)
                Text("${circle.memberCount} members • ${circle.groupSuccessRate * 100}% Staying Strong", style = MaterialTheme.typography.caption)
            }
            
            Button(
                onClick = onAction,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isJoined) ImpendColors.Error.copy(alpha = 0.1f) else ImpendColors.Primary
                ),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Text(
                    text = if (isJoined) "Leave" else "Join",
                    color = if (isJoined) ImpendColors.Error else Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}
