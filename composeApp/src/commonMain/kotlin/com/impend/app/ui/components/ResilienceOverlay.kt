package com.impend.app.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.impend.app.ui.theme.ImpendColors
import kotlinx.coroutines.delay

@Composable
fun ResilienceOverlay(
    prompt: String,
    onProceed: () -> Unit,
    onCancel: () -> Unit
) {
    var timer by remember { mutableStateOf(5) }
    val isTimerComplete = timer <= 0

    LaunchedEffect(Unit) {
        while (timer > 0) {
            delay(1000L)
            timer--
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.85f))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        AppCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Pause for Resilience",
                    style = MaterialTheme.typography.h6,
                    color = ImpendColors.Primary,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(24.dp))

                AnimatedContent(targetState = isTimerComplete) { complete ->
                    if (!complete) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = timer.toString(),
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Black,
                                color = ImpendColors.Secondary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Allowing your prefrontal cortex to reset...",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.caption
                            )
                        }
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = prompt,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.body1,
                                fontWeight = FontWeight.Medium,
                                lineHeight = 24.sp
                            )
                            
                            Spacer(modifier = Modifier.height(32.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                OutlinedButton(
                                    onClick = onCancel,
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray)
                                ) {
                                    Text("I'll pass")
                                }
                                
                                Button(
                                    onClick = onProceed,
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = ImpendColors.Success)
                                ) {
                                    Text("Proceed", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
