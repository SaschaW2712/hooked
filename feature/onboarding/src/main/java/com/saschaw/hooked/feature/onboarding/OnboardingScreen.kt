package com.saschaw.hooked.feature.onboarding

import HookedButton
import HookedButtonStyle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saschaw.hooked.core.designsystem.components.ColorStyle
import com.saschaw.hooked.core.designsystem.components.HighlightedText
import com.saschaw.hooked.core.designsystem.theme.HookedTheme

@Suppress("ktlint:standard:function-naming")
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    viewModel: OnboardingScreenViewModel = hiltViewModel(),
    onFinishOnboarding: () -> Unit,
) {
    val state = rememberScrollState()
    val authenticationManager = viewModel.authenticationManager

    val authLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            authenticationManager.onAuthorizationResult(it)
            onFinishOnboarding()
        }

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
                )
                .padding(horizontal = 36.dp, vertical = 36.dp)
                .verticalScroll(state),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Hooked", style = MaterialTheme.typography.headlineLarge)

            HighlightedText(ColorStyle.Primary, "Update your current projects")
            HighlightedText(ColorStyle.Secondary, "Find new inspiration")
            HighlightedText(ColorStyle.Tertiary, "Connect with the community")

            Spacer(Modifier.height(16.dp))

            Text(
                "But first, we need you to loop us in! Connect to Ravelry to get started.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = Center
            )

            HookedButton(
                onClick = {
                    authLauncher.launch(authenticationManager.getAuthorizationIntent())
                },
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                style = HookedButtonStyle.Primary,
                text = {
                    Text(
                        "Link my account",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(8.dp),
                    )
                },
            )

            Text(
                "Linking to Ravelry does not give Hooked access to any of your private information, like passwords or payment details.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
private fun BrowseScreenPreview() {
    HookedTheme {
        Surface {
            OnboardingScreen(snackbarHostState = remember { SnackbarHostState() }) {}
        }
    }
}

