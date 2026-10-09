package com.example.foucsedstudyapp.ui.screens.permissions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.foucsedstudyapp.navigation.Screen
import com.example.foucsedstudyapp.utils.isAccessibilityServiceEnabled
import com.example.foucsedstudyapp.utils.openAccessibilitySettings

@Composable
fun AccessibilityPermissionScreen(navController: NavController) {
    val context = LocalContext.current

    var isEnabled by remember {
        mutableStateOf(
            isAccessibilityServiceEnabled(context)
        )
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(isEnabled) {
        if (isEnabled) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.AccessibilityPermission.route) { inclusive = true }
            }
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                isEnabled = isAccessibilityServiceEnabled(context)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Enable App Blocking",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Enable Accessibility permission to let FocusedStudy detect and block distracting apps during your focus sessions.",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    openAccessibilitySettings(context)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Enable Accessibility",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
