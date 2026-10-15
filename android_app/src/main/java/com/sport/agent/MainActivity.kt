package com.sport.agent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.sport.agent.ui.navigation.MainScreen
import com.sport.agent.ui.screens.ControlViewModel
import com.sport.agent.ui.theme.SportAgentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ControlViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportAgentTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}
