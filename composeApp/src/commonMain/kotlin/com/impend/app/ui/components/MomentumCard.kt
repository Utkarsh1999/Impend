package com.impend.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.impend.app.ui.theme.ImpendColors
import com.impend.shared.domain.model.Circle

@Composable
fun MomentumCard(
    joinedCircles: List<Circle>,
    modifier: Modifier = Modifier
) {
    if (joinedCircles.isEmpty()) return

    AppCard(modifier = modifier.fillMaxWidth()) {
        Column {
            Text("Circle Momentum", style = MaterialTheme.typography.caption)
            Spacer(modifier = Modifier.height(4.dp))
            
            joinedCircles.take(2).forEach { circle ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(circle.name, style = MaterialTheme.typography.body2.copy(fontSize = 12.sp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${(circle.groupSuccessRate * 100).toInt()}% Strong",
                            style = MaterialTheme.typography.body2.copy(fontSize = 12.sp),
                            color = ImpendColors.Success
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("🔥", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
