package com.example.foucsedstudyapp.ui.screens.focus

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foucsedstudyapp.ui.theme.BrandPrimary
import com.example.foucsedstudyapp.ui.theme.FocusActive
import com.example.foucsedstudyapp.ui.theme.FocusPaused
import com.example.foucsedstudyapp.utils.FocusManager
import com.example.foucsedstudyapp.viewmodel.FocusViewModel
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun TimerScreen(
    navController: NavController,
    initialSeconds: Long,
    totalDuration: Long = initialSeconds,
    viewModel: FocusViewModel = viewModel()
) {
    val context = LocalContext.current
    val focusManager = remember { FocusManager(context) }
    val initiallyPaused = rememberSaveable { focusManager.isFocusPaused() }

    var timeLeftSeconds by rememberSaveable { mutableStateOf(initialSeconds) }
    var isRunning by rememberSaveable { mutableStateOf(!initiallyPaused) }
    var showEndDialog by remember { mutableStateOf(false) }

    val maxSeconds = remember { totalDuration }

    // Intercept back button to prevent accidental exit
    BackHandler {
        showEndDialog = true
    }

    // Timer countdown
    LaunchedEffect(isRunning) {
        while (isRunning && timeLeftSeconds > 0) {
            delay(1000)
            if (isRunning && timeLeftSeconds > 0) {
                timeLeftSeconds -= 1
            }
        }
    }

    // Session completion
    LaunchedEffect(timeLeftSeconds) {
        if (timeLeftSeconds == 0L) {
            isRunning = false
            focusManager.stopFocus()
            viewModel.completeFocusSession(
                context = context,
                durationMinutes = maxOf(1, (totalDuration / 60).toInt()),
                onComplete = {
                    navController.navigate("home") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }

    val minutes = timeLeftSeconds / 60
    val seconds = timeLeftSeconds % 60
    val progress = if (maxSeconds > 0) timeLeftSeconds.toFloat() / maxSeconds else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 500),
        label = "TimerProgress"
    )

    val activeColor by animateColorAsState(
        targetValue = if (isRunning) BrandPrimary else FocusPaused,
        animationSpec = tween(durationMillis = 400),
        label = "ColorProgress"
    )

    fun handleEndSession() {
        isRunning = false
        focusManager.stopFocus()
        val elapsedSeconds = maxSeconds - timeLeftSeconds
        val elapsedMinutes = (elapsedSeconds / 60).toInt()
        viewModel.saveIncompleteFocusSession(
            context = context,
            durationMinutes = elapsedMinutes,
            onComplete = {
                navController.navigate("home") {
                    popUpTo(0) { inclusive = true }
                }
            }
        )
    }

    if (showEndDialog) {
        AlertDialog(
            onDismissRequest = { showEndDialog = false },
            title = {
                Text(text = "End Focus Session?", fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    text = "Are you sure you want to stop? Your focus time of ${(maxSeconds - timeLeftSeconds) / 60} minutes will be saved to your stats."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showEndDialog = false
                        handleEndSession()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("End Session")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEndDialog = false }) {
                    Text("Stay Focused")
                }
            }
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(36.dp))

            // Subtitle badge
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
            ) {
                Text(
                    text = if (isRunning) "🎯 DEEP WORK IN PROGRESS" else "⏸️ SESSION PAUSED",
                    style = MaterialTheme.typography.labelMedium,
                    color = activeColor,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(54.dp))

            // Minimalist Glowing Circular Progress Indicator
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(280.dp)
            ) {
                // Background Track
                CircularProgressIndicator(
                    progress = { 1f },
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                    strokeWidth = 12.dp,
                    strokeCap = StrokeCap.Round
                )

                // Active Progress Arc
                CircularProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.fillMaxSize(),
                    color = activeColor,
                    strokeWidth = 12.dp,
                    strokeCap = StrokeCap.Round
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds),
                        fontSize = 64.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onBackground,
                        letterSpacing = (-1).sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = if (isRunning) "TIME REMAINING" else "PAUSED",
                        color = activeColor,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.5.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Controls Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Play / Pause Circular Button
                Surface(
                    onClick = {
                        if (isRunning) {
                            isRunning = false
                            focusManager.pauseFocus(timeLeftSeconds)
                        } else {
                            isRunning = true
                            focusManager.resumeFocus(timeLeftSeconds)
                        }
                    },
                    modifier = Modifier
                        .size(64.dp)
                        .shadow(8.dp, shape = CircleShape, spotColor = activeColor),
                    shape = CircleShape,
                    color = activeColor
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isRunning) "Pause Session" else "Resume Session",
                            modifier = Modifier.size(30.dp),
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.width(20.dp))

                // End Session Button
                OutlinedButton(
                    onClick = { showEndDialog = true },
                    modifier = Modifier
                        .height(60.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f),
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Stop,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "End Session",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Distraction Shield Active Pill Badge
            Surface(
                color = if (isRunning) {
                    FocusActive.copy(alpha = 0.1f)
                } else {
                    FocusPaused.copy(alpha = 0.1f)
                },
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(
                    1.dp,
                    if (isRunning) FocusActive.copy(alpha = 0.3f) else FocusPaused.copy(alpha = 0.3f)
                ),
                modifier = Modifier.padding(bottom = 28.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = if (isRunning) FocusActive else FocusPaused,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = if (isRunning) "Distraction Shield Active" else "Shield Paused • Apps Accessible",
                        color = if (isRunning) FocusActive else FocusPaused,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
